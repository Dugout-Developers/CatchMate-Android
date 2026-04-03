package com.catchmate.presentation.viewmodel.notification

sealed interface NotificationEvent {
    object InitData : NotificationEvent

    object OnBackClicked : NotificationEvent

    data class OnItemSwiped(
        val id: Long,
    ) : NotificationEvent

    data class OnItemClicked(
        val id: Long,
    ) : NotificationEvent

    object OnMoreListLoaded : NotificationEvent
}
