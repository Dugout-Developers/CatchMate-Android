package com.catchmate.domain.model.support

import com.catchmate.domain.model.user.Club

data class AdminUserInfo(
    val userId: Long,
    val profileImageUrl: String,
    val nickName: String,
    val clubInfo: Club,
    val gender: String,
    val email: String,
    val socialType: String,
    val joinedAt: String,
)
