package com.catchmate.data.mapper

import com.catchmate.data.dto.BoardWriteRequestDTO
import com.catchmate.data.dto.BoardWriteResponseDTO
import com.catchmate.domain.model.BoardWriteRequest
import com.catchmate.domain.model.BoardWriteResponse

object BoardWriteMapper {
    fun toBoardWriteRequestDTO(boardWriteRequest: BoardWriteRequest): BoardWriteRequestDTO =
        BoardWriteRequestDTO(
            title = boardWriteRequest.title,
            gameDate = boardWriteRequest.gameDate,
            location = boardWriteRequest.location,
            homeTeam = boardWriteRequest.homeTeam,
            awayTeam = boardWriteRequest.awayTeam,
            maxPerson = boardWriteRequest.maxPerson,
            cheerTeam = boardWriteRequest.cheerTeam,
            preferGender = boardWriteRequest.preferGender,
            preferAge = boardWriteRequest.preferAge,
            addInfo = boardWriteRequest.addInfo,
        )

    fun toBoardWriteResponse(boardWriteResponseDTO: BoardWriteResponseDTO): BoardWriteResponse =
        BoardWriteResponse(
            boardId = boardWriteResponseDTO.boardId,
        )
}
