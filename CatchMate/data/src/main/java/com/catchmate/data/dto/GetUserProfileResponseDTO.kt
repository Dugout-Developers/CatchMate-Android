package com.catchmate.data.dto

data class GetUserProfileResponseDTO(
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
