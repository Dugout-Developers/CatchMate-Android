package com.catchmate.data.dto.enroll

import com.catchmate.data.dto.board.BoardDTO

data class GetEnrollResponseDTO(
    val enrollId: Long,
    val acceptStatus: String,
    val description: String,
    val requestDate: String,
    val applicant: UserInfoDTO,
    val boardResponse: BoardDTO,
)
