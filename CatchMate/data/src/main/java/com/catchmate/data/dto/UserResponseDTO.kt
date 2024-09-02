package com.catchmate.data.dto

data class UserResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val createdAt: String,
)
