package com.catchmate.domain.model

data class GetUserProfileResponse(
    val userId: Long,
    val email: String,
    val picture: String,
    val gender: String,
    val pushAgreement: String,
    val nickName: String,
    val favoriteGudan: String,
    val description: String?,
    val birthDate: String,
    val watchStyle: String?,
)
