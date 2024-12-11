package com.catchmate.data.dto

data class GetReceivedNotificationResponseDTO(
    val notificationID: Long,
    val boardInfo: BoardInfoDTO,
    val title: String,
    val body: String,
    val createdAt: String,
    val read: Boolean,
)
