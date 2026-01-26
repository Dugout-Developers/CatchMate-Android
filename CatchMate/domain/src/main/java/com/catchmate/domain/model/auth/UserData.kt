package com.catchmate.domain.model.auth

data class UserData(
    val email: String,
    val profileImageUrl: String,
    val providerId: String,
    val provider: String,
    val fcmToken: String,
)
