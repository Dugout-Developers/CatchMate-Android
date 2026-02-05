package com.catchmate.domain.model.support

data class GetInquiryResponse(
    val inquiryId: Long,
    val nickname: String,
    val type: String,
    val content: String,
    val answer: String,
    val status: String,
    val createdAt: String,
)
