package com.catchmate.data.mapper

import com.catchmate.data.dto.GetBoardPagingResponseDTO
import com.catchmate.data.dto.GetBoardResponseDTO
import com.catchmate.data.dto.PostBoardRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import com.catchmate.data.dto.PutBoardRequestDTO
import com.catchmate.data.dto.PutBoardResponseDTO
import com.catchmate.data.dto.WriterDTO
import com.catchmate.domain.model.GetBoardResponse
import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.model.PutBoardRequest
import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse
import com.catchmate.domain.model.PutBoardResponse
import com.catchmate.domain.model.Writer

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

    fun toGetBoardPagingResponse(responseDTOList: List<GetBoardPagingResponseDTO>): List<GetBoardPagingResponse> =
        responseDTOList.map { response ->
            GetBoardPagingResponse(
                boardId = response.boardId,
                title = response.title,
                gameDate = response.gameDate,
                location = response.location,
                homeTeam = response.homeTeam,
                awayTeam = response.awayTeam,
                cheerTeam = response.cheerTeam,
                currentPerson = response.currentPerson,
                maxPerson = response.maxPerson,
            )
        }

    fun toGetBoardResponse(responseDTO: GetBoardResponseDTO): GetBoardResponse =
        GetBoardResponse(
            boardId = responseDTO.boardId,
            writer = toWriter(responseDTO.writer),
            title = responseDTO.title,
            gameDate = responseDTO.gameDate,
            location = responseDTO.location,
            homeTeam = responseDTO.homeTeam,
            awayTeam = responseDTO.awayTeam,
            cheerTeam = responseDTO.cheerTeam,
            maxPerson = responseDTO.maxPerson,
            currentPerson = responseDTO.currentPerson,
            preferGender = responseDTO.preferGender,
            preferAge = responseDTO.preferAge,
            addInfo = responseDTO.addInfo,
        )

    fun toWriter(writerDTO: WriterDTO): Writer =
        Writer(
            userId = writerDTO.userId,
            nickName = writerDTO.nickName,
            picture = writerDTO.picture,
            favGudan = writerDTO.favGudan,
            watchStyle = writerDTO.watchStyle,
            gender = writerDTO.gender,
            birthDate = writerDTO.birthDate,
        )
}