package com.catchmate.data.dto.enroll

data class EnrollContentDTO(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val userInfo: EnrollUserInfoDTO,
    val boardInfo: EnrollBoardInfoDTO,
)
