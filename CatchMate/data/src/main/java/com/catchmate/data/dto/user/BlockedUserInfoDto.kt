package com.catchmate.data.dto.user

data class BlockedUserInfoDto(
    val blockId: Long,
    val userId: Long,
    val nickName: String,
    val profileImageUrl: String,
    val blockedAt: String?,
)
