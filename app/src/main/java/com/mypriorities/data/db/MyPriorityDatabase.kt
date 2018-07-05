package com.mypriorities.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Usman on 01/04/2018.
 */
@Database(entities = arrayOf(MyPriorityEntity::class), version = 1)
abstract class MyPriorityDatabase : RoomDatabase() {
    abstract fun getMyPriorityDao(): MyPriorityDao
}