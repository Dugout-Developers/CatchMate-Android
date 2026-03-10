package com.catchmate.presentation.viewmodel.home

sealed interface HomeSideEffect {
    object NavigateToNotification : HomeSideEffect

    object NavigateToLogin : HomeSideEffect

    data class NavigateToReadPost(
        val boardId: Long,
    ) : HomeSideEffect

    object ShowDatePickerBottomSheet : HomeSideEffect

    object ShowClubBottomSheet : HomeSideEffect

    object ShowMemberCountBottomSheet : HomeSideEffect
}
