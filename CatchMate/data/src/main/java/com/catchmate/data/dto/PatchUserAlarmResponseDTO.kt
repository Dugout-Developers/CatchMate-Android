package com.catchmate.data.dto

data class PatchUserAlarmResponseDTO(
    val userId: Long,
    val pushAgreement: String,
    val createdAt: String,
)
