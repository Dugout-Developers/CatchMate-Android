package com.catchmate.data.mapper

import com.catchmate.data.dto.BoardListResponseDTO
import com.catchmate.domain.model.BoardListResponse

object BoardListMapper {
    fun toBoardListResponse(response: List<BoardListResponseDTO>): List<BoardListResponse> =
        response.map { boardListResponseDTO ->
            BoardListResponse(
                boardId = boardListResponseDTO.boardId,
                title = boardListResponseDTO.title,
                gameDate = boardListResponseDTO.gameDate,
                location = boardListResponseDTO.location,
                homeTeam = boardListResponseDTO.homeTeam,
                awayTeam = boardListResponseDTO.awayTeam,
                currentPerson = boardListResponseDTO.currentPerson,
                maxPerson = boardListResponseDTO.maxPerson,
            )
        }
}
