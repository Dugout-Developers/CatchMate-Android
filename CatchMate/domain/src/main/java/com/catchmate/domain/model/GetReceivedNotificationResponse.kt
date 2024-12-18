package com.catchmate.domain.model

data class GetReceivedNotificationResponse(
    val notificationID: Long,
    val boardInfo: BoardInfo,
    val title: String,
    val body: String,
    val createdAt: String,
    val read: Boolean,
)
