package com.catchmate.data.dto.notification

data class GetNotificationListResponseDTO(
    val content: List<NotificationInfoDTO>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
