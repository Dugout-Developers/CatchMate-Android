package com.catchmate.domain.model

data class PatchUserProfileRequest(
    val request: UserProfileRequest,
    val profileImage: String,
)
