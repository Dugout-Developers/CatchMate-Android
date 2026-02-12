package com.catchmate.data.dto.support

data class PostUserReportRequestDTO(
    val reportedUserId: Long,
    val reason: String,
    val description: String,
)
