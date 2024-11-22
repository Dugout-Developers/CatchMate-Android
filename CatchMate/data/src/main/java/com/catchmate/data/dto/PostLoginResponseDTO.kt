package com.catchmate.data.dto

data class PostLoginResponseDTO(
    val accessToken: String?,
    val refreshToken: String?,
    val isFirstLogin: Boolean,
)
