package com.catchmate.data.dto.chatting

data class GetChattingRoomListResponseDTO(
    val content: List<ChatRoomInfoDTO>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
