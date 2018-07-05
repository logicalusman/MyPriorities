package com.mypriorities.common

import java.text.SimpleDateFormat

object DateTimeUtils {

    // date time format supported by todoist.com
    const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"

    fun createDatetimeString(date: String, time: String): String {
        val d = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(String.format("%s %s", date, time))
        return SimpleDateFormat(DATE_TIME_FORMAT).format(d)
    }

    fun extractDateAndTime(datetime: String): Pair<String, String> {
        val d = SimpleDateFormat(DATE_TIME_FORMAT).parse(datetime)
        val wantedDate = SimpleDateFormat("dd-MM-yyyy").format(d)
        val wantedTime = SimpleDateFormat("HH:mm").format(d)
        return Pair<String, String>(wantedDate, wantedTime)
    }
}