package com.catchmate.data.mapper

import com.catchmate.data.dto.BoardEditRequestDTO
import com.catchmate.data.dto.BoardWriteRequestDTO
import com.catchmate.data.dto.BoardWriteResponseDTO
import com.catchmate.domain.model.BoardEditRequest
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

    fun toBoardEditRequestDTO(boardEditRequest: BoardEditRequest): BoardEditRequestDTO =
        BoardEditRequestDTO(
            boardId = boardEditRequest.boardId,
            title = boardEditRequest.title,
            gameDate = boardEditRequest.gameDate,
            location = boardEditRequest.location,
            homeTeam = boardEditRequest.homeTeam,
            awayTeam = boardEditRequest.awayTeam,
            cheerTeam = boardEditRequest.cheerTeam,
            currentPerson = boardEditRequest.currentPerson,
            maxPerson = boardEditRequest.maxPerson,
            preferGender = boardEditRequest.preferGender,
            preferAge = boardEditRequest.preferAge,
            addInfo = boardEditRequest.addInfo,
        )
}
