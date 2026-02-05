package com.catchmate.domain.model.user

data class GetUserAlarmResponse(
    val allAlarm: Boolean,
    val chatAlarm: Boolean,
    val enrollAlarm: Boolean,
    val eventAlarm: Boolean,
)
