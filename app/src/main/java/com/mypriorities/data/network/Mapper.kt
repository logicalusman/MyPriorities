package com.mypriorities.data.network

import com.mypriorities.common.DateTimeUtils
import com.mypriorities.data.network.request.MyPriorityRequest
import com.mypriorities.data.network.response.MyPriorityResponse
import com.mypriorities.domain.MyPriority

object Mapper {

    fun toMyPriorityList(myPriorityResponseList: List<MyPriorityResponse>): List<MyPriority> {
        val myPriorityList = ArrayList<MyPriority>(myPriorityResponseList.size)
        myPriorityResponseList.forEachIndexed { _, value ->
            myPriorityList.add(toMyPriority(value))

        }
        return myPriorityList
    }

    fun toMyPriority(myPriorityResponse: MyPriorityResponse): MyPriority {
        val dateTimePair = DateTimeUtils.extractDateAndTime(myPriorityResponse.myPriorityDueDateTime.datetime)
        return MyPriority(id = myPriorityResponse.id, description = myPriorityResponse.description, date = dateTimePair.first, time = dateTimePair.second, completed = myPriorityResponse.completed, level = myPriorityResponse.level)
    }

    fun toMyPriorityRequest(myPriority: MyPriority): MyPriorityRequest {
        return MyPriorityRequest(myPriority.description, DateTimeUtils.createDatetimeString(myPriority.date, myPriority.time), myPriority.level, myPriority.completed)
    }
}