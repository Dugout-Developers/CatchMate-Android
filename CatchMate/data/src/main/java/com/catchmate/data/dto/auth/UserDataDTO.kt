package com.catchmate.data.dto.auth

data class UserDataDTO(
    val email: String,
    val profileImageUrl: String,
    val providerId: String,
    val provider: String,
    val fcmToken: String,
)
