package com.catchmate.data.dto.enroll

import com.catchmate.data.dto.user.ClubDTO

data class UserInfoDTO(
    val userId: Long,
    val nickName: String,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val birthDate: String,
    val watchStyle: String? = null,
    val club: ClubDTO,
)
