package com.catchmate.data.dto

data class PatchUserProfileRequestDTO(
    val request: UserProfileRequestDTO,
    val profileImage: String,
)
