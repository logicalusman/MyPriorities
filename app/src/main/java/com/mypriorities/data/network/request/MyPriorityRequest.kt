package com.mypriorities.data.network.request

import com.google.gson.annotations.SerializedName

data class MyPriorityRequest(@field:SerializedName("content") val description: String,
                             @field:SerializedName("due_datetime") val datetime: String,
                             @field:SerializedName("priority") val level: Int = 1,
                             var completed: Boolean = false
)