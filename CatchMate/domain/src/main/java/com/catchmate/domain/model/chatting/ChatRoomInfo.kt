package com.catchmate.domain.model.chatting

import com.catchmate.domain.model.board.Board

data class ChatRoomInfo(
    val chatRoomId: Long,
    val boardInfo: Board,
    val participantCount: Int,
    val lastMessageAt: String,
)
