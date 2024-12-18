package com.catchmate.domain.model

data class ReceivedEnrollContent(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val requestDate: String,
    val userInfo: EnrollUserInfo,
    val boardInfo: EnrollBoardInfo,
    val new: Boolean,
)
