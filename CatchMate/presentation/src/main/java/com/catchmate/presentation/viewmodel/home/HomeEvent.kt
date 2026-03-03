package com.catchmate.presentation.viewmodel.home

import com.catchmate.presentation.view.components.FilterSheetType
import java.time.LocalDate

sealed interface HomeEvent {
    object InitData : HomeEvent
    object OnNotificationClicked : HomeEvent
    data class OnDateFilterClicked(val date: LocalDate?) : HomeEvent
    object OnClubFilterClicked : HomeEvent
    object OnMemberFilterClicked : HomeEvent
    data class OnDateFilterApplied(val date: LocalDate) : HomeEvent
    data class OnClubFilterApplied(val clubIds: List<Int>) : HomeEvent
    data class OnMemberFilterApplied(val memberCount: String) : HomeEvent
    data class OnBoardItemClicked(val boardId: Long) : HomeEvent
    object OnLoadMoreBoards : HomeEvent
    data class OnBoardDeleted(val boardId: Long) : HomeEvent
    data class OnFilterReset(val filterSheetType: FilterSheetType) : HomeEvent
}
