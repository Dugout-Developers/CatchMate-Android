package com.catchmate.data.dto.enroll

import com.catchmate.data.dto.user.ClubDTO

data class EnrollBoardInfoDTO(
    val boardId: Long,
    val title: String,
    val content: String,
    val currentPerson: Int,
    val maxPerson: Int,
    val bookMarked: Boolean,
    val cheerClub: ClubDTO,
    val gameResponse: GameInfoDTO,
    val userResponse: UserInfoDTO,
)
