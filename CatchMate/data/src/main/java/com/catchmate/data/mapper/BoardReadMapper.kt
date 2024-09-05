package com.catchmate.data.mapper

import com.catchmate.data.dto.BoardReadResponseDTO
import com.catchmate.data.dto.WriterDTO
import com.catchmate.domain.model.BoardReadResponse
import com.catchmate.domain.model.Writer

object BoardReadMapper {
    fun toBoardReadResponse(boardReadResponseDTO: BoardReadResponseDTO): BoardReadResponse =
        BoardReadResponse(
            boardId = boardReadResponseDTO.boardId,
            writer = toWriter(boardReadResponseDTO.writer),
            title = boardReadResponseDTO.title,
            gameDate = boardReadResponseDTO.gameDate,
            location = boardReadResponseDTO.location,
            homeTeam = boardReadResponseDTO.homeTeam,
            awayTeam = boardReadResponseDTO.awayTeam,
            cheerTeam = boardReadResponseDTO.cheerTeam,
            maxPerson = boardReadResponseDTO.maxPerson,
            currentPerson = boardReadResponseDTO.currentPerson,
            preferGender = boardReadResponseDTO.preferGender,
            preferAge = boardReadResponseDTO.preferAge,
            addInfo = boardReadResponseDTO.addInfo,
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
