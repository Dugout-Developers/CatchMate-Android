package com.catchmate.data.dto

data class PatchUserProfileRequestDTO(
    val nickName: String,
    val description: String,
    val favGudan: String,
    val watchStyle: String? = null,
)
