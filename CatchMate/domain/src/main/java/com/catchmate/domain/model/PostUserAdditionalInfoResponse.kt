package com.catchmate.domain.model

data class PostUserAdditionalInfoResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: String,
)
