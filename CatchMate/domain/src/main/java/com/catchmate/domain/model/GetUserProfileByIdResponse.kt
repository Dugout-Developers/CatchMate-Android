package com.catchmate.domain.model

data class GetUserProfileByIdResponse(
    val userId: Long,
    val email: String,
    val picture: String,
    val gender: String,
    val pushAgreement: String,
    val nickName: String,
    val favoriteGudan: String,
    val birthDate: String,
    val watchStyle: String,
)
