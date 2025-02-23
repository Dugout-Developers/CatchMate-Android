package com.catchmate.data.dto.support

data class PostUserReportRequestDTO(
    val reportedUserId: Long,
    val reportType: String,
    val content: String,
)
