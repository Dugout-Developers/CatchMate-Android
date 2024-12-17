package com.catchmate.domain.model

data class EnrollUserInfo(
    val userId: Long,
    val nickName: String,
    val picture: String,
    val favGudan: String,
    val watchStyle: String? = null,
    val gender: String,
    val birthDate: String,
)
