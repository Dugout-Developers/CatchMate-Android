package com.catchmate.domain.model.enroll

data class EnrollContent(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val userInfo: EnrollUserInfo,
    val boardInfo: EnrollBoardInfo,
)
