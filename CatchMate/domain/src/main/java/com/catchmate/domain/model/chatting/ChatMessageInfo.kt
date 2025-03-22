package com.catchmate.domain.model.chatting

data class ChatMessageInfo(
    val id: ChatMessageId,
    val chatMessageId: String,
    val roomId: Long? = null,
    val content: String,
    val senderId: Long,
    val messageType: String,
)
