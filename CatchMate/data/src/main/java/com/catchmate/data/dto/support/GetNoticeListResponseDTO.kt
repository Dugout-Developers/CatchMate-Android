package com.catchmate.data.dto.support

data class GetNoticeListResponseDTO(
    val content: List<NoticeListInfoDto>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
