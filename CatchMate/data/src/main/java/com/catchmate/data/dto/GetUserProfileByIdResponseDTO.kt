package com.catchmate.data.dto

data class GetUserProfileByIdResponseDTO(
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
