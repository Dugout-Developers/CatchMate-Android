package com.catchmate.data.dto

data class UserProfileRequestDTO(
    val nickName: String,
    val favoriteClubId: Int,
    val watchStyle: String?,
)
