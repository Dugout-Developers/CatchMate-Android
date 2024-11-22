package com.catchmate.domain.model

data class PostLoginRequest(
    val email: String,
    val providerId: String,
    val provider: String,
    val picture: String,
    val fcmToken: String,
)
