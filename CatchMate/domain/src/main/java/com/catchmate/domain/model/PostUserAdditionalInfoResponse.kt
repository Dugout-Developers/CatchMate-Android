package com.catchmate.domain.model

data class PostUserAdditionalInfoResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val createdAt: String,
)
