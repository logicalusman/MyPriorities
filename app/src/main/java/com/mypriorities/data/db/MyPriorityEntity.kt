package com.mypriorities.data.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Usman on 01/04/2018.
 */
@Entity(tableName = "mypriority")
data class MyPriorityEntity(
        @PrimaryKey val id: Long,
        val description: String,
        val date: String,
        val time: String,
        val level: Int,
        val completed: Boolean
)

