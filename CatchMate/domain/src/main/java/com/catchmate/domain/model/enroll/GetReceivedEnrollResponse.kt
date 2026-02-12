package com.catchmate.domain.model.enroll

data class GetReceivedEnrollResponse(
    val content: List<ReceivedEnrollInfoResponse>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
