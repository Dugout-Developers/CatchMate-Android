package com.catchmate.domain.model.board

import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import com.catchmate.domain.model.user.Club

data class GetTempBoardResponse(
    val boardId: Long,
    val title: String?,
    val content: String?,
    val maxPerson: Int,
    val preferredGender: String?,
    val preferredAgeRange: String,
    val cheerClub: Club?,
    val game: GameInfo?,
    val user: UserInfo,
)
