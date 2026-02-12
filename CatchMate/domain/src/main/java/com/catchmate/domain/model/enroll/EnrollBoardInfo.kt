package com.catchmate.domain.model.enroll

import com.catchmate.domain.model.user.Club

data class EnrollBoardInfo(
    val boardId: Long,
    val title: String,
    val content: String,
    val currentPerson: Int,
    val maxPerson: Int,
    val bookMarked: Boolean,
    val cheerClub: Club,
    val gameResponse: GameInfo,
    val userResponse: UserInfo,
)
