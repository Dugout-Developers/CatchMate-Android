package com.catchmate.data.dto.board

import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.user.ClubDTO

data class GetTempBoardResponseDTO(
    val boardId: Long,
    val title: String?,
    val content: String?,
    val maxPerson: Int,
    val preferredGender: String?,
    val preferredAgeRange: String,
    val cheerClub: ClubDTO?,
    val game: GameInfoDTO?,
    val user: UserInfoDTO,
)
