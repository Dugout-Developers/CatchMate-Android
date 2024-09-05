package com.catchmate.domain.model

data class Writer(
    val userId: Long,
    val nickName: String,
    val picture: String,
    val favGudan: String,
    val watchStyle: String,
    val gender: String,
    val birthDate: String,
)
