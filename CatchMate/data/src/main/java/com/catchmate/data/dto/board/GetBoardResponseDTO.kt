package com.catchmate.data.dto.board

import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.user.ClubDTO

data class GetBoardResponseDTO(
    val boardId: Long,
    val title: String,
    val content: String,
    val currentPerson: Int,
    val maxPerson: Int,
    val preferredGender: String?,
    val preferredAgeRange: String,
    val liftUpDate: String,
    val bookMarked: Boolean,
    val buttonStatus: String,
    val myEnrollId: Long?,
    val chatRoomId: Long,
    val cheerClub: ClubDTO,
    val game: GameInfoDTO,
    val user: UserInfoDTO,
)
