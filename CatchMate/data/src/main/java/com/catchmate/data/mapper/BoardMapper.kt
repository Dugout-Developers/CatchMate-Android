package com.catchmate.data.mapper

import com.catchmate.data.dto.PostBoardRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import com.catchmate.data.dto.PutBoardRequestDTO
import com.catchmate.data.dto.PutBoardResponseDTO
import com.catchmate.domain.model.PutBoardRequest
import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse
import com.catchmate.domain.model.PutBoardResponse

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

    fun toPutBoardRequestDTO(request: PutBoardRequest): PutBoardRequestDTO =
        PutBoardRequestDTO(
            boardId = request.boardId,
            title = request.title,
            gameDate = request.gameDate,
            location = request.location,
            homeTeam = request.homeTeam,
            awayTeam = request.awayTeam,
            cheerTeam = request.cheerTeam,
            currentPerson = request.currentPerson,
            maxPerson = request.maxPerson,
            preferGender = request.preferGender,
            preferAge = request.preferAge,
            addInfo = request.addInfo,
        )

    fun toPutBoardResponse(responseDTO: PutBoardResponseDTO): PutBoardResponse =
        PutBoardResponse(
            boardId = responseDTO.boardId,
        )
}