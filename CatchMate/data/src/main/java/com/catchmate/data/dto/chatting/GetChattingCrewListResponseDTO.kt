package com.catchmate.data.dto.chatting

data class GetChattingCrewListResponseDTO(
    val memberId: Long,
    val userId: Long,
    val nickName: String,
    val profileImageUrl: String,
    val joinedAt: String,
)
