package com.mypriorities.data.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mypriorities.domain.MyPriority
import kotlinx.coroutines.experimental.launch

class DBRepository(private val myPriorityDatabase: MyPriorityDatabase) {


    fun addNewPriority(myPriority: MyPriority?): LiveData<Boolean> {

        val savePriorityLiveData = MutableLiveData<Boolean>()
        myPriority?.let {
            val myPriorityEntity = MyPriorityEntity(id = it.id!!, description = it.description, date = it.date, time = it.time, completed = it.completed, level = it.level)
            launch {
                myPriorityDatabase.getMyPriorityDao().insertNewPriority(myPriorityEntity)
            }
        }
        savePriorityLiveData.postValue(true)
        return savePriorityLiveData
    }

    fun getAllPriorities(): LiveData<List<MyPriority>>? {
        return myPriorityDatabase.getMyPriorityDao().getAllSavedPriorities()
    }

    fun deletePriority(priorityId: Long) {
        launch {
            myPriorityDatabase.getMyPriorityDao().deleteByUserId(priorityId)
        }
    }

}



