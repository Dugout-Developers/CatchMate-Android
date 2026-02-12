package com.catchmate.domain.model.user

data class PostUserBlockResponse(
    val targetUserId: Long,
    val message: String,
)
