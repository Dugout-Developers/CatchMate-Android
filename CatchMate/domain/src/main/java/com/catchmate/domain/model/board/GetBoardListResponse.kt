package com.catchmate.domain.model.board

data class GetBoardListResponse(
    val boardInfoList: List<Board>,
    val totalPages: Int,
    val totalElements: Int,
    val isLast: Boolean,
) {
    companion object {
        val EMPTY = GetBoardListResponse(emptyList(), 0, 0, true)
    }
}
