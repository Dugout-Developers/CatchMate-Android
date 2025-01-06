package com.catchmate.data.dto.enroll

data class ReceivedEnrollContentDTO(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val requestDate: String,
    val userInfo: EnrollUserInfoDTO,
    val boardInfo: EnrollBoardInfoDTO,
    val new: Boolean,
)
