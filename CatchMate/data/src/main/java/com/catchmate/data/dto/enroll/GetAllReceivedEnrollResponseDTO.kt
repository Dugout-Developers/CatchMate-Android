package com.catchmate.data.dto.enroll

data class GetAllReceivedEnrollResponseDTO(
    val content: List<AllReceivedEnrollInfoResponseDTO>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
