package com.catchmate.presentation.viewmodel.favorite

import com.catchmate.domain.model.board.Board

data class FavoriteUiState(
    val boardList: List<Board>? = emptyList(), // 서버 에러 시 예외처리 위해 nullable
    val pageNumber: Int = 0,
    val hasNext: Boolean = false,
)
