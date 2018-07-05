package com.mypriorities.data.network

import com.mypriorities.domain.MyPriority
import com.mypriorities.domain.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

class NetworkRepository @Inject constructor(var myPrioritiesApi: MyPrioritiesApi) {

    private val TAG = "NetworkRepository"

    fun addNewPriority(myPriority: MyPriority): Observable<Result<MyPriority>> {
        val addNewPrioritySubject = SingleSubject.create<Result<MyPriority>>()
        myPrioritiesApi.addMyPriority(myPriorityRequest = Mapper.toMyPriorityRequest(myPriority)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    addNewPrioritySubject.onSuccess(Result.fomData(Mapper.toMyPriority(it)))
                },
                {
                    addNewPrioritySubject.onSuccess(Result.fromError(it))
                })
        return addNewPrioritySubject.toObservable()
    }

    fun getAllPriorities(): Observable<Result<List<MyPriority>>> {
        val allPrioritiesSubject = SingleSubject.create<Result<List<MyPriority>>>()
        myPrioritiesApi.getMyPriorities().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    allPrioritiesSubject.onSuccess(Result.fomData(Mapper.toMyPriorityList(it)))
                },
                {
                    allPrioritiesSubject.onSuccess(Result.fromError(it))
                })

        return allPrioritiesSubject.toObservable()
    }


    fun deletePriority(myPriorityId: Long): Observable<Result<Boolean>> {
        val deletePrioritySubject = SingleSubject.create<Result<Boolean>>()
        myPrioritiesApi.deleteMyPriority(myPriorityId = "${myPriorityId}").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    deletePrioritySubject.onSuccess(Result.fomData(true))
                },
                {
                    deletePrioritySubject.onSuccess(Result.fromError(it))
                })

        return deletePrioritySubject.toObservable()
    }


}