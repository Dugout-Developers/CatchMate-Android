package com.catchmate.data.dto.support

data class GetInquiryResponseDTO(
    val inquiryId: Long,
    val type: String,
    val content: String,
    val answer: String,
    val status: String,
    val createdAt: String,
)
