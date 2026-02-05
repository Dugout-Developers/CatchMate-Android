package com.catchmate.data.dto.user

data class GetUserAlarmResponseDto(
    val allAlarm: Boolean,
    val chatAlarm: Boolean,
    val enrollAlarm: Boolean,
    val eventAlarm: Boolean,
)
