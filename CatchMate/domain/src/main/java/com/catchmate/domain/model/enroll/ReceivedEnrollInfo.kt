package com.catchmate.domain.model.enroll

data class ReceivedEnrollInfo(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val receiveDate: String,
    val userInfo: UserInfo,
    val boardInfo: EnrollBoardInfo,
    val new: Boolean,
)
