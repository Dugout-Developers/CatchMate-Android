package com.catchmate.data.dto.chatting

import com.catchmate.data.dto.board.BoardDTO

data class ChatRoomInfoDTO(
    val chatRoomId: Long,
    val boardInfo: BoardDTO,
    val participantCount: Int,
    val lastMessageAt: String,
)
