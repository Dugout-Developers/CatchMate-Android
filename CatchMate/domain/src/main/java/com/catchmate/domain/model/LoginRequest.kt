package com.catchmate.domain.model

data class LoginRequest(
    val providerId: String,
    val provider: String,
    val email: String,
    val picture: String,
    val fcmToken: String,
)
