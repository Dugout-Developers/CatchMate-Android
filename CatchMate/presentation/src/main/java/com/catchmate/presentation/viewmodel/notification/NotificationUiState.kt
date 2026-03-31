package com.catchmate.presentation.viewmodel.notification

import com.catchmate.domain.model.notification.NotificationInfo

data class NotificationUiState(
    val notificationList: List<NotificationInfo>? = emptyList(),
    val pageNumber: Int = 0,
    val hasNext: Boolean = false,
)
