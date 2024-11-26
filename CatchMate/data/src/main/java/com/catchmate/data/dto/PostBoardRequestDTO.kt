package com.catchmate.data.dto

data class PostBoardRequestDTO(
    val title: String,
    val gameDate: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val maxPerson: Int,
    val cheerTeam: String,
    val preferGender: String? = null,
    val preferAge: Int? = null,
    val addInfo: String,
)
