package com.catchmate.domain.model.enroll

data class GameInfo(
    val homeClubId: Int,
    val awayClubId: Int,
    val gameStartDate: String,
    val location: String,
)
