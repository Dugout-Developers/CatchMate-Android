package com.catchmate.data.dto.enroll

data class GetReceivedEnrollResponseDTO(
    val content: List<ReceivedEnrollInfoResponseDTO>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
