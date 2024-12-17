package com.catchmate.data.dto

data class EnrollUserInfoDTO(
    val userId: Long,
    val nickName: String,
    val picture: String,
    val favGudan: String,
    val watchStyle: String? = null,
    val gender: String,
    val birthDate: String,
)
