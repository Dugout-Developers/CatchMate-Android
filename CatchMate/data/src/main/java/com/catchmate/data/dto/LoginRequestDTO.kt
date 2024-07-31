package com.catchmate.data.dto

data class LoginRequestDTO(
    val providerId: String,
    val provider: String,
    val email: String,
    val picture: String,
    val fcmToken: String,
)
