package com.catchmate.data.dto.chatting

data class LastMessageInfoDto(
    val messageId: Long,
    val chatRoomId: Long,
    val senderId: Long,
    val senderNickName: String,
    val senderProfileImageUrl: String,
    val content: String,
    val messageType: String,
    val createdAt: String,
)
