package com.catchmate.data.dto.notification

data class NotificationInfoDTO(
    val id: Long,
    val title: String,
    val alarmType: String,
    val read: Boolean,
    val createdAt: String,
    val senderProfileImageUrl: String?,
    val gameInfo: String,
)
