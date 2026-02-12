package com.catchmate.domain.model.board

data class GetUserBoardListResponse(
    val content: List<Board>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
