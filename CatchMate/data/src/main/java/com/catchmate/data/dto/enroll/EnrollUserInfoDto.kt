package com.catchmate.data.dto.enroll

data class EnrollUserInfoDto(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val gender: String,
    val ageRange: String,
    val favoriteClub: String,
    val watchStyle: String?,
)
