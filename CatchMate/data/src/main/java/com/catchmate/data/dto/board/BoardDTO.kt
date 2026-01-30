package com.catchmate.data.dto.board

import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.user.ClubDTO

data class BoardDTO(
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
