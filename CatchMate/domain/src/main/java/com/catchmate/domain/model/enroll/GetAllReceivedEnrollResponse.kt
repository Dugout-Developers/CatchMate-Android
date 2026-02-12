package com.catchmate.domain.model.enroll

data class GetAllReceivedEnrollResponse(
    val content: List<AllReceivedEnrollInfoResponse>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
