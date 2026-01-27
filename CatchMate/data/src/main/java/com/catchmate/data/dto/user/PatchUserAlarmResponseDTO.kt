package com.catchmate.data.dto.user

import com.catchmate.domain.model.enumclass.AlarmType

data class PatchUserAlarmResponseDTO(
    val alarmType: AlarmType,
    val enabled: Boolean,
)
