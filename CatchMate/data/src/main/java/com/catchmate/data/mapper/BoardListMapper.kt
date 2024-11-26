package com.catchmate.data.mapper

import com.catchmate.data.dto.GetBoardPagingResponseDTO
import com.catchmate.domain.model.GetBoardPagingResponse

object BoardListMapper {
    fun toBoardListResponse(response: List<GetBoardPagingResponseDTO>): List<GetBoardPagingResponse> =
        response.map { boardListResponseDTO ->
            GetBoardPagingResponse(
                boardId = boardListResponseDTO.boardId,
                title = boardListResponseDTO.title,
                gameDate = boardListResponseDTO.gameDate,
                location = boardListResponseDTO.location,
                homeTeam = boardListResponseDTO.homeTeam,
                awayTeam = boardListResponseDTO.awayTeam,
                cheerTeam = boardListResponseDTO.cheerTeam,
                currentPerson = boardListResponseDTO.currentPerson,
                maxPerson = boardListResponseDTO.maxPerson,
            )
        }
}
