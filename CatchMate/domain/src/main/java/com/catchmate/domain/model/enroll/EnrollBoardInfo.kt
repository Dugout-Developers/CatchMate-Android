package com.catchmate.domain.model.enroll

data class EnrollBoardInfo(
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
