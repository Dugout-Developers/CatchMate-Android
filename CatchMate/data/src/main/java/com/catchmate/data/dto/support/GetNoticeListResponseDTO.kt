package com.catchmate.data.dto.support

data class GetNoticeListResponseDTO(
    val content: List<NoticeInfoDTO>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
