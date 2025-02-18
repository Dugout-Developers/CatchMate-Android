package com.catchmate.domain.model.chatting

data class GetChattingHistoryResponse(
    val chatMessageInfoList: List<ChatMessageInfo>,
    val totalPages: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
)
