package com.catchmate.data.dto.chatting

data class GetChattingHistoryResponseDTO(
    val chatMessageInfoList: List<ChatMessageInfoDTO>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
)
