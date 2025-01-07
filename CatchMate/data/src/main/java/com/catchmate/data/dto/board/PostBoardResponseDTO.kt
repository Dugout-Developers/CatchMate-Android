package com.catchmate.data.dto.board

import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.enroll.GameInfoDTO

data class PostBoardResponseDTO(
    val boardId: Long,
    val title: String,
    val content: String,
    val cheerClubId: Int,
    val currentPerson: Int,
    val maxPerson: Int,
    val preferredGender: String,
    val preferredAgeRange: List<String>,
    val gameInfo: GameInfoDTO,
    val liftUpDate: String,
    val userInfo: UserInfoDTO,
)
