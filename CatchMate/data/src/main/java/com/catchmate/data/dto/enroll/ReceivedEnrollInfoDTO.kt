package com.catchmate.data.dto.enroll

data class ReceivedEnrollInfoDTO(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val receiveDate: String,
    val userInfo: UserInfoDTO,
    val boardInfo: EnrollBoardInfoDTO,
    val new: Boolean,
)
