package com.catchmate.data.dto.support

import com.catchmate.data.dto.user.ClubDTO

data class AdminUserInfoDTO(
    val userId: Long,
    val profileImageUrl: String,
    val nickName: String,
    val clubInfo: ClubDTO,
    val gender: String,
    val email: String,
    val socialType: String,
    val joinedAt: String,
)
