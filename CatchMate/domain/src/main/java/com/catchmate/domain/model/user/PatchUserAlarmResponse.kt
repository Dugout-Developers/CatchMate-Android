package com.catchmate.domain.model.user

import com.catchmate.domain.model.enumclass.AlarmType

data class PatchUserAlarmResponse(
    val alarmType: AlarmType,
    val enabled: Boolean,
)
