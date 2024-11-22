package com.catchmate.domain.model

data class PostLoginResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val isFirstLogin: Boolean,
)
