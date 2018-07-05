package com.mypriorities.data

import android.arch.lifecycle.LiveData
import com.mypriorities.data.db.DBRepository
import com.mypriorities.data.network.NetworkRepository
import com.mypriorities.domain.MyPriority
import com.mypriorities.domain.Result
import io.reactivex.Observable
import javax.inject.Inject

/**
 * <p>
 * Facade for data operations, a.k.a repository pattern. This class is a central point for
 * viewmodels to perform data operations without bothering if an operation is performed on network or
 * to a local db or, even, both. It is also a perfect place for caching network data.
 * </p>
 * <p>
 *  MyPriorityRepository manages its network operations via NetworkRepository while uses DBRepository as a cache.
 *  NetworkRepository uses Retrofit with Rx. DBRepository uses Room along LiveData. Once a network operation successfully
 *  returns, data is synced with cache i.e. DBRepository.
 * </p>
 *
 * @author Usman
 */
class MyPriorityRepository @Inject constructor(val networkRepository: NetworkRepository, val dbRepository: DBRepository) {

    fun addNewPriority(myPriority: MyPriority): Observable<Result<MyPriority>> {
        return networkRepository.addNewPriority(myPriority).map {
            savePriorityToDB(it)
            return@map it
        }
    }

    private fun savePriorityToDB(myPriorityResult: Result<MyPriority>) {
        if (myPriorityResult.success) {
            dbRepository.addNewPriority(myPriorityResult.data)
        }
    }

    fun deletePriority(myPriorityId: Long): Observable<Result<Boolean>> {
        return networkRepository.deletePriority(myPriorityId).map {
            if (it.success) {
                deletePriorityFromDB(myPriorityId)
            }
            return@map it
        }
    }

    private fun deletePriorityFromDB(myPriorityId: Long) {
        dbRepository.deletePriority(myPriorityId)
    }

    fun getAllPriorities(): LiveData<List<MyPriority>>? {
        networkRepository.getAllPriorities().subscribe({
            if (it.success && it.data?.size!! > 0) {
                it.data.forEach {
                    dbRepository.addNewPriority(it)
                }
            }
        })
        return dbRepository.getAllPriorities()
    }

}