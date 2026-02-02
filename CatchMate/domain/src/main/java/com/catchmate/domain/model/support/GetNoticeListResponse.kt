package com.catchmate.domain.model.support

data class GetNoticeListResponse(
    val content: List<NoticeInfo>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
