package com.catchmate.domain.model

data class PatchUserProfileRequest(
    val nickName: String,
    val description: String,
    val favGudan: String,
    val watchStyle: String? = null,
)
