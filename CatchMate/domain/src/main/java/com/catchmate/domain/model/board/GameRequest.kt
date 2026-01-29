package com.catchmate.domain.model.board

data class GameRequest(
    val homeClubId: Int,
    val awayClubId: Int,
    val gameStartDate: String,
    val location: String,
)
