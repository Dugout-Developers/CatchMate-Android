package com.catchmate.domain.model

data class PatchUserAlarmResponse(
    val userId: Long,
    val pushAgreement: String,
    val createdAt: String,
)
