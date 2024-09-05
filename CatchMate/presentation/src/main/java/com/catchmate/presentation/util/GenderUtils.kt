package com.catchmate.presentation.util

import android.content.Context
import com.catchmate.presentation.R

object GenderUtils {
    fun convertPostGender(gender: String): String? =
        when (gender) {
            "여성" -> "F"
            "남성" -> "M"
            else -> null
        }

    fun convertBoardGender(
        context: Context,
        gender: String,
    ): String =
        when (gender) {
            "F" -> context.getString(R.string.female)
            else -> context.getString(R.string.male)
        }
}
