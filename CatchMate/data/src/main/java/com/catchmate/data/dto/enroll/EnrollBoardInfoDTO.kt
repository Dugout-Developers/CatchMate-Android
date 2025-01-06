package com.catchmate.data.dto.enroll

data class EnrollBoardInfoDTO(
    val boardInfo: Long,
    val title: String,
    val gameDate: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val cheerTeam: String,
    val currentPerson: Int,
    val maxPerson: Int,
    val addInfo: String,
)
