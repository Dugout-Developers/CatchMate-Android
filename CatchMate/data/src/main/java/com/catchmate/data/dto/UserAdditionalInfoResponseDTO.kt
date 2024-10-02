package com.catchmate.data.dto

data class UserAdditionalInfoResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val createdAt: String,
)
