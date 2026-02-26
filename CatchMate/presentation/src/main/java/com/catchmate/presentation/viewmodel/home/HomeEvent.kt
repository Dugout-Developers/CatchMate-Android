package com.catchmate.presentation.viewmodel.home

sealed interface HomeEvent {
    object InitData : HomeEvent
    object OnNotificationClicked : HomeEvent
    data class OnDateFilterClicked(val date: String) : HomeEvent
    object OnClubFilterClicked : HomeEvent
    object OnMemberFilterClicked : HomeEvent
    data class OnBoardItemClicked(val boardId: Long) : HomeEvent
    object OnLoadMoreBoards : HomeEvent
    data class OnBoardDeleted(val boardId: Long) : HomeEvent
}
