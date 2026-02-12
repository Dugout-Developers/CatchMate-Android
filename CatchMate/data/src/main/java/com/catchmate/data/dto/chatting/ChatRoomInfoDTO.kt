package com.catchmate.data.dto.chatting

import com.catchmate.data.dto.board.BoardDTO

data class ChatRoomInfoDTO(
    val chatRoomId: Long,
    val board: BoardDTO,
    val lastMessage: LastMessageInfoDto?,
    val createdAt: String,
)
