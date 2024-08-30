package com.catchmate.domain.model

data class UserResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val createdAt: String,
)
