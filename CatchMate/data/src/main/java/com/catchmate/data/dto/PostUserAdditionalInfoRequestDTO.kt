package com.catchmate.data.dto

data class PostUserAdditionalInfoRequestDTO(
    val email: String,
    val providerId: String,
    val provider: String,
    val profileImageUrl: String,
    val fcmToken: String,
    val gender: String,
    val nickName: String,
    val birthDate: String,
    val favoriteClubId: String,
    val watchStyle: String,
)
