package com.catchmate.domain.model

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isFirstLogin: Boolean,
)
