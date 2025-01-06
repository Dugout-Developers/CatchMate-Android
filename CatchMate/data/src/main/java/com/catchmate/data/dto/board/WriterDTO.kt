package com.catchmate.data.dto.board

data class WriterDTO(
    val userId: Long,
    val nickName: String,
    val picture: String,
    val favGudan: String,
    val watchStyle: String,
    val gender: String,
    val birthDate: String,
)
