package com.catchmate.domain.model.notification

data class BoardInfo(
    val boardId: Long,
    val title: String,
    val gameDate: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val cheerTeam: String,
    val currentPerson: Long,
    val maxPerson: Long,
    val addInfo: String,
)
