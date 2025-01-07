package com.catchmate.data.mapper

import com.catchmate.data.dto.board.BoardDTO
import com.catchmate.data.dto.board.DeleteBoardRequestDTO
import com.catchmate.data.dto.board.GetBoardListResponseDTO
import com.catchmate.data.dto.board.GetBoardResponseDTO
import com.catchmate.data.dto.board.GetLikedBoardResponseDTO
import com.catchmate.data.dto.board.PostBoardRequestDTO
import com.catchmate.data.dto.board.PostBoardResponseDTO
import com.catchmate.data.dto.board.PutBoardRequestDTO
import com.catchmate.data.dto.board.PutBoardResponseDTO
import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.user.FavoriteClubDTO
import com.catchmate.domain.model.board.Board
import com.catchmate.domain.model.board.DeleteBoardRequest
import com.catchmate.domain.model.board.GetBoardListResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
import com.catchmate.domain.model.board.PutBoardRequest
import com.catchmate.domain.model.board.PutBoardResponse
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

    fun toGetBoardListResponse(responseDTO: GetBoardListResponseDTO): GetBoardListResponse =
        GetBoardListResponse(
            boardInfoList = responseDTO.boardInfoList.map { toBoard(it) }
        )

    private fun toBoard(dto: BoardDTO): Board =
        Board(
            boardId = dto.boardId,
            title = dto.title,
            content = dto.content,
            cheerClubId = dto.cheerClubId,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            preferredGender = dto.preferredGender,
            gameInfo = toGameInfo(dto.gameInfo),
            liftUpDate = dto.liftUpDate,
            userInfo = toUserInfo(dto.userInfo),
        )

    fun toGetBoardResponse(responseDTO: GetBoardResponseDTO): GetBoardResponse =
        GetBoardResponse(
            boardId = responseDTO.boardId,
            title = responseDTO.title,
            content = responseDTO.content,
            cheerClubId = responseDTO.cheerClubId,
            currentPerson = responseDTO.currentPerson,
            maxPerson = responseDTO.maxPerson,
            preferredGender = responseDTO.preferredGender,
            preferredAgeRange = responseDTO.preferredAgeRange,
            gameInfo = toGameInfo(responseDTO.gameInfo),
            liftUpDate = responseDTO.liftUpDate,
            userInfo = toUserInfo(responseDTO.userInfo),
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
