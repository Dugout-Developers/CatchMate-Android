package com.catchmate.domain.model

data class UserProfileRequest(
    val nickName: String,
    val favoriteClubId: Int,
    val watchStyle: String? = null,
)
