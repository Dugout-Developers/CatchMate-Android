package com.catchmate.data.dto.board

data class GetBoardListResponseDTO(
    val content: List<BoardDTO>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
