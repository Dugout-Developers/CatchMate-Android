package com.catchmate.data.dto

import com.catchmate.domain.model.AlarmType

data class PatchUserAlarmResponseDTO(
    val userId: Long,
    val alarmType: AlarmType,
    val isEnabled: String,
    val createdAt: String,
)
