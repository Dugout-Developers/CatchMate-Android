package com.catchmate.presentation.viewmodel.home

import com.catchmate.domain.model.board.Board

data class HomeUiState(
    val dateFilterData: String = "",
    val clubFilterData: List<Int> = emptyList(),
    val memberFilterData: String = "",
    val boardList: List<Board> = emptyList(),
    val pageNumber: Int = 0,
    val hasNext: Boolean = false,
)
