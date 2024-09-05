package com.catchmate.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun formatBirthDate(inputDate: String): String {
        val year = inputDate.substring(0..1)
        val month = inputDate.substring(2..3)
        val day = inputDate.substring(4..5)

        return if (year.toInt() in 0..50) {
            "20$year-$month-$day"
        } else {
            "19$year-$month-$day"
        }
    }

    fun formatGameDateTime(
        date: String,
        time: String,
    ): String = "$date $time:00"

    fun formatPlayDate(dateTime: String): String {
        val (date, time) = dateTime.split(" ", "T")

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate: Date = inputDateFormat.parse(date)
        val outputDateFormat = SimpleDateFormat("M월 d일 E요일", Locale.getDefault())

        val formattedTime = time.substring(0, 5)

        return outputDateFormat.format(formattedDate) + " | " + formattedTime
    }
}
