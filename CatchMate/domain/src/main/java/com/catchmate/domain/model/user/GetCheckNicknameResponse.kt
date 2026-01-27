package com.catchmate.domain.model.user

data class GetCheckNicknameResponse(
    val nickName: String,
    val available: Boolean,
)