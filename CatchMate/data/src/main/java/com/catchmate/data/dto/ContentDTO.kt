package com.catchmate.data.dto

data class ContentDTO(
    val notificationId: Long,
    val boardInfo: BoardInfoDTO,
    val title: String,
    val body: String,
    val createdAt: String,
    val read: Boolean,
)
