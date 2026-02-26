package com.catchmate.presentation.viewmodel.home

sealed interface HomeSideEffect {
    data object NavigateToNotification : HomeSideEffect
    data object NavigateToLogin : HomeSideEffect
    data class NavigateToReadPost(val boardId: Long) : HomeSideEffect
    data object ShowDatePickerBottomSheet : HomeSideEffect
    data object ShowClubBottomSheet : HomeSideEffect
    data object ShowMemberCountBottomSheet : HomeSideEffect
}
