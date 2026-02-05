package com.catchmate.domain.model.chatting

data class GetChattingRoomListResponse(
    val content: List<ChatRoomInfo>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
