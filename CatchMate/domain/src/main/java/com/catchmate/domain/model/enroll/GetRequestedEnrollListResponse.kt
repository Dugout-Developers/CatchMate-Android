package com.catchmate.domain.model.enroll

data class GetRequestedEnrollListResponse(
    val content: List<EnrollInfo>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
