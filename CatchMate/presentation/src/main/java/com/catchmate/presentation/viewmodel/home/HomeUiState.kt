package com.catchmate.presentation.viewmodel.home

import com.catchmate.domain.model.board.Board
import java.time.LocalDate

data class HomeUiState(
    val selectedDate: LocalDate? = null,
    val clubFilterData: List<Int> = emptyList(),
    val memberFilterData: String = "",
    val boardList: List<Board>? = emptyList(),
    val pageNumber: Int = 0,
    val hasNext: Boolean = false,
)
