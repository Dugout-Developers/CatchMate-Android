package com.catchmate.data.dto.board

data class GetBoardPagingResponseDTO(
    val boardId: Long,
    val title: String,
    val gameDate: String,
    val location: String,
    val homeTeam: String,
    val awayTeam: String,
    val cheerTeam: String,
    val currentPerson: Int,
    val maxPerson: Int,
)
