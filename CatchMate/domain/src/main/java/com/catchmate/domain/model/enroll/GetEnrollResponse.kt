package com.catchmate.domain.model.enroll

import com.catchmate.domain.model.board.Board

data class GetEnrollResponse(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val requestDate: String,
    val applicant: UserInfo,
    val boardResponse: Board,
)
