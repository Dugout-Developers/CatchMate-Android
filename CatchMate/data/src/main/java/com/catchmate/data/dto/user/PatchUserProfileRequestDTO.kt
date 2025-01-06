package com.catchmate.data.dto.user

data class PatchUserProfileRequestDTO(
    val request: UserProfileRequestDTO,
    val profileImage: String,
)
