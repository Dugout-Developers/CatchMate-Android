package com.catchmate.domain.model.user

data class GetBlockedUserListResponse(
    val content: List<BlockedUserInfo>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalElements: Int,
    val hasNext: Boolean,
)
