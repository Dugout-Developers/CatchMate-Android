package com.catchmate.data.mapper

import com.catchmate.data.dto.PostBoardRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse

object BoardMapper {
    fun toPostBoardRequestDTO(request: PostBoardRequest): PostBoardRequestDTO =
        PostBoardRequestDTO(
            title = request.title,
            gameDate = request.gameDate,
            location = request.location,
            homeTeam = request.homeTeam,
            awayTeam = request.awayTeam,
            maxPerson = request.maxPerson,
            cheerTeam = request.cheerTeam,
            preferGender = request.preferGender,
            preferAge = request.preferAge,
            addInfo = request.addInfo,
        )

    fun toPostBoardResponse(responseDTO: PostBoardResponseDTO): PostBoardResponse =
        PostBoardResponse(
            boardId = responseDTO.boardId,
        )
}