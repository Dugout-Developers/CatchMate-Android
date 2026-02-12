package com.catchmate.data.dto.enroll

data class GetRequestedEnrollListResponseDTO(
    val content: List<EnrollInfoDTO>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
