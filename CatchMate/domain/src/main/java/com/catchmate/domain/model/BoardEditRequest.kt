package com.catchmate.domain.model

data class BoardEditRequest(
    val boardId: Long,
    val title: String,
    val gameDate: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val cheerTeam: String,
    val currentPerson: Int,
    val maxPerson: Int,
    val preferGender: String? = null,
    val preferAge: Int? = null,
    val addInfo: String,
)
