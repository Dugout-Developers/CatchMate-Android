package com.catchmate.domain.model.support

data class PostUserReportRequest(
    val reportedUserId: Long,
    val reportType: String,
    val content: String,
)
