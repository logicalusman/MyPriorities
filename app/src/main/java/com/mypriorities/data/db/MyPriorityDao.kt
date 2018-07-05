package com.mypriorities.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.mypriorities.domain.MyPriority

/**
 * Created by Usman on 01/04/2018.
 */
@Dao
interface MyPriorityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewPriority(myPriorityEntity: MyPriorityEntity)

    @Query("DELETE FROM mypriority WHERE id = :priorityId")
    fun deleteByUserId(priorityId: Long)

    @Query("SELECT id,description,date,time,completed,level FROM mypriority")
    fun getAllSavedPriorities(): LiveData<List<MyPriority>>?
}