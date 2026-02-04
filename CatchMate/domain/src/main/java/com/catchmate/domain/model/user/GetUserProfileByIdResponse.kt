package com.catchmate.domain.model.user

data class GetUserProfileByIdResponse(
    val userId: Long,
    val nickName: String,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val birthDate: String,
    val watchStyle: String?,
    val club: Club,
)
