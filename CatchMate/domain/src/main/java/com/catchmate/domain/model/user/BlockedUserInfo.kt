package com.catchmate.domain.model.user

data class BlockedUserInfo(
    val blockId: Long,
    val userId: Long,
    val nickName: String,
    val profileImageUrl: String,
    val blockedAt: String?,
)
