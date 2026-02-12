package com.catchmate.domain.model.user

data class DeleteBlockedUserResponse(
    val targetUserId: Long,
    val message: Boolean,
)
