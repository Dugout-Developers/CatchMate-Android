package com.catchmate.data.dto.user

data class UserProfileRequestDTO(
    val nickName: String,
    val favoriteClubId: Int,
    val watchStyle: String?,
)
