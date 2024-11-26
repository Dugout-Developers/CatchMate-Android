package com.catchmate.data.mapper

import com.catchmate.data.dto.BoardEditRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import com.catchmate.domain.model.BoardEditRequest
import com.catchmate.domain.model.PostBoardResponse

object BoardWriteMapper {
    fun toBoardWriteResponse(boardWriteResponseDTO: PostBoardResponseDTO): PostBoardResponse =
        PostBoardResponse(
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
