package com.catchmate.domain.model.notification

data class GetNotificationListResponse(
    val content: List<NotificationInfo>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
