package com.catchmate.domain.model.chatting

import com.catchmate.domain.model.board.Board

data class ChatRoomInfo(
    val chatRoomId: Long,
    val board: Board,
    val lastMessage: LastMessageInfo?,
    val createdAt: String,
)
