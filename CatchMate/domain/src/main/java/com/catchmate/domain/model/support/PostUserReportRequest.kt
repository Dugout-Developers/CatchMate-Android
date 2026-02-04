package com.catchmate.domain.model.support

data class PostUserReportRequest(
    val reportedUserId: Long,
    val reason: String,
    val description: String,
)
