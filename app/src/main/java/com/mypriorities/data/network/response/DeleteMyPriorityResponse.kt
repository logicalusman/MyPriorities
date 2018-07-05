package com.mypriorities.data.network.response

import com.google.gson.annotations.SerializedName

class DeleteMyPriorityResponse(@field:SerializedName("task_id") val id: Long,
                               @field:SerializedName("content") val description: String)
