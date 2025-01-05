package com.catchmate.domain.model

data class PatchUserAlarmResponse(
    val userId: Long,
    val alarmType: AlarmType,
    val isEnabled: String,
    val createdAt: String,
)
