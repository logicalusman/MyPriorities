package com.mypriorities.domain

/**
 * Created by Usman on 24/03/2018.
 * level = 1 to 4 where 1 = Normal and 4 being the highest
 */
data class MyPriority(
        var id: Long? = null,
        var description: String,
        var date: String,
        var time: String,
        var level: Int = 1,
        var completed: Boolean = false
)