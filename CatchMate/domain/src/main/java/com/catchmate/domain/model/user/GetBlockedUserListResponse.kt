package com.catchmate.domain.model.user

data class GetBlockedUserListResponse(
    val userInfoList: List<GetUserProfileResponse>,
    val totalPages: Int,
    val totalElements: Long,
    val isFirst: Boolean,
    val isLast: Boolean,
)
