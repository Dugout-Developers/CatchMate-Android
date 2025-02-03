package com.catchmate.data.dto.board

data class GetBoardListResponseDTO(
    val boardInfoList: List<BoardDTO>,
    val totalPages: Int,
    val totalElements: Int,
    val isLast: Boolean,
)
