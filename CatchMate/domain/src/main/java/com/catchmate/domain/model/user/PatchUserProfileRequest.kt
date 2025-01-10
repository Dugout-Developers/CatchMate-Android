package com.catchmate.domain.model.user

data class PatchUserProfileRequest(
    val request: UserProfileRequest,
    val profileImage: String,
)
