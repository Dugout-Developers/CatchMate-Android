package com.catchmate.domain.model.notification

data class NotificationInfo(
    val id: Long,
    val title: String,
    val alarmType: String,
    var read: Boolean,
    val createdAt: String,
    val senderProfileImageUrl: String?,
    val gameInfo: String,
)
