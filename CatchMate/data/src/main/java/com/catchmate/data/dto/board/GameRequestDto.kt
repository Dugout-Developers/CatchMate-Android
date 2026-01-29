package com.catchmate.data.dto.board

data class GameRequestDto(
    val homeClubId: Int?,
    val awayClubId: Int?,
    val gameStartDate: String?,
    val location: String?,
)
