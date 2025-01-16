package com.catchmate.domain.model.notification

import com.catchmate.domain.model.board.Board

data class NotificationInfo(
    val notificationId: Long,
    val boardInfo: Board,
    val senderProfileImageUrl: String,
    val title: String,
    val body: String,
    val createdAt: String,
    var read: Boolean,
)
