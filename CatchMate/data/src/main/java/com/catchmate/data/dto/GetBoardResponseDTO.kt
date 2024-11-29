package com.catchmate.data.dto

data class GetBoardResponseDTO(
    val boardId: Long,
    val writer: WriterDTO,
    val title: String,
    val gameDate: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val cheerTeam: String,
    val maxPerson: Int,
    val currentPerson: Int,
    val preferGender: String? = null,
    val preferAge: Int? = null,
    val addInfo: String,
)
