package com.catchmate.data.dto.user

data class GetBlockedUserListResponseDTO(
    val content: List<BlockedUserInfoDto>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
