package com.catchmate.domain.model

data class EnrollContent(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val userInfo: EnrollUserInfo,
    val boardInfo: EnrollBoardInfo,
)
