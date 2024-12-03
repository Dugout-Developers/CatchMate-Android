package com.catchmate.domain.model

data class Content(
    val notificationId: Long,
    val boardInfo: BoardInfo,
    val title: String,
    val body: String,
    val createdAt: String,
    val read: Boolean,
)
