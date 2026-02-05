package com.catchmate.domain.model.enroll

data class EnrollUserInfo(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val gender: String,
    val ageRange: String,
    val favoriteClub: String,
    val watchStyle: String?,
)
