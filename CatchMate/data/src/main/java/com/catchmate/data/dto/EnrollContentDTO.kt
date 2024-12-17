package com.catchmate.data.dto

data class EnrollContentDTO(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val userInfo: EnrollUserInfoDTO,
    val boardInfo: EnrollBoardInfoDTO,
)
