package com.catchmate.data.dto.enroll

import com.catchmate.data.dto.user.ClubDTO

data class GameInfoDTO(
    val gameId: Int,
    val gameStartDate: String?,
    val location: String?,
    val homeClub: ClubDTO?,
    val awayClub: ClubDTO?,
)
