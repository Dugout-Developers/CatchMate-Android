package com.catchmate.data.mapper

import com.catchmate.data.dto.board.DeleteBoardRequestDTO
import com.catchmate.data.dto.board.GetBoardPagingResponseDTO
import com.catchmate.data.dto.board.GetBoardResponseDTO
import com.catchmate.data.dto.board.GetLikedBoardResponseDTO
import com.catchmate.data.dto.board.PostBoardRequestDTO
import com.catchmate.data.dto.board.PostBoardResponseDTO
import com.catchmate.data.dto.board.PutBoardRequestDTO
import com.catchmate.data.dto.board.PutBoardResponseDTO
import com.catchmate.data.dto.board.WriterDTO
import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.user.FavoriteClubDTO
import com.catchmate.domain.model.board.DeleteBoardRequest
import com.catchmate.domain.model.board.GetBoardPagingResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
import com.catchmate.domain.model.board.PutBoardRequest
import com.catchmate.domain.model.board.PutBoardResponse
import com.catchmate.domain.model.board.Writer
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import com.catchmate.domain.model.user.FavoriteClub

object BoardMapper {
    fun toPostBoardRequestDTO(request: PostBoardRequest): PostBoardRequestDTO =
        PostBoardRequestDTO(
            title = request.title,
            content = request.content,
            maxPerson = request.maxPerson,
            cheerClubId = request.cheerClubId,
            preferredGender = request.preferredGender,
            preferredAgeRange = request.preferredAgeRange,
            gameRequest = toGameRequestDTO(request.gameRequest),
            isCompleted = request.isCompleted,
        )

    private fun toGameRequestDTO(request: GameInfo): GameInfoDTO =
        GameInfoDTO(
            homeClubId = request.homeClubId,
            awayClubId = request.awayClubId,
            gameStartDate = request.gameStartDate,
            location = request.location,
        )

    fun toPostBoardResponse(dto: PostBoardResponseDTO): PostBoardResponse =
        PostBoardResponse(
            boardId = dto.boardId,
            title = dto.title,
            content = dto.content,
            cheerClubId = dto.cheerClubId,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            preferredGender = dto.preferredGender,
            preferredAgeRange = dto.preferredAgeRange,
            gameInfo = toGameInfo(dto.gameInfo),
            liftUpDate = dto.liftUpDate,
            userInfo = toUserInfo(dto.userInfo),
        )

    private fun toGameInfo(dto: GameInfoDTO): GameInfo =
        GameInfo(
            homeClubId = dto.homeClubId,
            awayClubId = dto.awayClubId,
            gameStartDate = dto.gameStartDate,
            location = dto.location,
        )

    private fun toUserInfo(dto: UserInfoDTO): UserInfo =
        UserInfo(
            userId = dto.userId,
            email = dto.email,
            profileImageUrl = dto.profileImageUrl,
            gender = dto.gender,
            allAlarm = dto.allAlarm,
            chatAlarm = dto.chatAlarm,
            enrollAlarm = dto.enrollAlarm,
            eventAlarm = dto.eventAlarm,
            nickName = dto.nickName,
            favoriteClub = toFavoriteClub(dto.favoriteClub),
            birthDate = dto.birthDate,
            watchStyle = dto.watchStyle,
        )

    private fun toFavoriteClub(dto: FavoriteClubDTO): FavoriteClub =
        FavoriteClub(
            id = dto.id,
            name = dto.name,
            homeStadium = dto.homeStadium,
            region = dto.region,
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

    private fun toWriter(writerDTO: WriterDTO): Writer =
        Writer(
            userId = writerDTO.userId,
            nickName = writerDTO.nickName,
            picture = writerDTO.picture,
            favGudan = writerDTO.favGudan,
            watchStyle = writerDTO.watchStyle,
            gender = writerDTO.gender,
            birthDate = writerDTO.birthDate,
        )

    fun toDeleteBoardRequestDTO(request: DeleteBoardRequest): DeleteBoardRequestDTO =
        DeleteBoardRequestDTO(
            boardId = request.boardId,
        )

    fun toGetLikedBoardResponse(responseDTO: List<GetLikedBoardResponseDTO>): List<GetLikedBoardResponse> =
        responseDTO.map { board ->
            GetLikedBoardResponse(
                boardId = board.boardId,
                title = board.title,
                gameDate = board.gameDate,
                location = board.location,
                homeTeam = board.homeTeam,
                awayTeam = board.awayTeam,
                cheerTeam = board.cheerTeam,
                currentPerson = board.currentPerson,
                maxPerson = board.maxPerson,
            )
        }
}
