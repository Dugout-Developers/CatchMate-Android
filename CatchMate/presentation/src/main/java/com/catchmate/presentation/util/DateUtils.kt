package com.catchmate.presentation.util

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
}
