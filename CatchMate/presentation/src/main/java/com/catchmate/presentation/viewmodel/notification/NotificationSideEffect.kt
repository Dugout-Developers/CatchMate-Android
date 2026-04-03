package com.catchmate.presentation.viewmodel.notification

sealed interface NotificationSideEffect {
    object NavigateToBack : NotificationSideEffect

    object NavigateToLogin : NotificationSideEffect

    object NavigateToDetail : NotificationSideEffect

    data class ShowSnackBar(
        val stringResource: Int,
    ) : NotificationSideEffect
}
