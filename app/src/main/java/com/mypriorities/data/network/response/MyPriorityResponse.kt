package com.mypriorities.data.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Usman on 24/03/2018.
 * level = 1 to 4 where 1 = Normal and 4 being the highest
 * level = 1 to 4 where 1 = Normal and 4 being the highest
 */
data class MyPriorityResponse(@field:SerializedName("id") val id: Long,
                              @field:SerializedName("content") val description: String,
                              @field:SerializedName("due") val myPriorityDueDateTime: MyPriorityDueDateTime,
                              @field:SerializedName("priority") val level: Int,
                              var completed: Boolean
)

data class MyPriorityDueDateTime(val recurring: Boolean, val datetime:String)
