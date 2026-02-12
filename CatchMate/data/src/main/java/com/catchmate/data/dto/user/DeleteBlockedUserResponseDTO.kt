package com.catchmate.data.dto.user

data class DeleteBlockedUserResponseDTO(
    val targetUserId: Long,
    val message: Boolean,
)
