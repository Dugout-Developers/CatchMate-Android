package com.catchmate.data.dto

data class LoginResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val isFirstLogin: Boolean,
)
