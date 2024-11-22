package com.catchmate.data.dto

data class PostLoginRequestDTO(
    val email: String,
    val providerId: String,
    val provider: String,
    val picture: String,
    val fcmToken: String,
)
