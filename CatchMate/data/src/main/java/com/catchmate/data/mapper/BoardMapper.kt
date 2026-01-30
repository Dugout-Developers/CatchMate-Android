package com.catchmate.data.mapper

import com.catchmate.data.dto.board.BoardDTO
import com.catchmate.data.dto.board.GameRequestDto
import com.catchmate.data.dto.board.GetBoardListResponseDTO
import com.catchmate.data.dto.board.GetBoardResponseDTO
import com.catchmate.data.dto.board.GetLikedBoardResponseDTO
import com.catchmate.data.dto.board.GetTempBoardResponseDTO
import com.catchmate.data.dto.board.GetUserBoardListResponseDTO
import com.catchmate.data.dto.board.PatchBoardLiftUpResponseDTO
import com.catchmate.data.dto.board.PostBoardRequestDTO
import com.catchmate.data.dto.board.PostBoardResponseDTO
import com.catchmate.data.dto.board.PutBoardRequestDTO
import com.catchmate.data.dto.board.PutBoardResponseDTO
import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.user.ClubDTO
import com.catchmate.domain.model.board.Board
import com.catchmate.domain.model.board.GameRequest
import com.catchmate.domain.model.board.GetBoardListResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.model.board.GetTempBoardResponse
import com.catchmate.domain.model.board.GetUserBoardListResponse
import com.catchmate.domain.model.board.PatchBoardLiftUpResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
import com.catchmate.domain.model.board.PutBoardRequest
import com.catchmate.domain.model.board.PutBoardResponse
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import com.catchmate.domain.model.user.Club

object BoardMapper {
    fun toPostBoardRequestDTO(request: PostBoardRequest): PostBoardRequestDTO =
        PostBoardRequestDTO(
            boardId = request.boardId,
            title = request.title,
            content = request.content,
            maxPerson = request.maxPerson,
            cheerClubId = request.cheerClubId,
            preferredGender = request.preferredGender,
            preferredAgeRange = request.preferredAgeRange,
            completed = request.completed,
            gameRequest = toGameRequestDto(request.gameRequest),
        )

    private fun toGameRequestDto(request: GameRequest?): GameRequestDto? =
        if (request == null) {
            null
        } else {
            GameRequestDto(
                homeClubId = request.homeClubId,
                awayClubId = request.awayClubId,
                gameStartDate = request.gameStartDate,
                location = request.location,
            )
        }

    private fun toGameInfoDTO(game: GameInfo): GameInfoDTO =
        GameInfoDTO(
            gameId = game.gameId,
            gameStartDate = game.gameStartDate,
            location = game.location,
            homeClub = toClubDto(game.homeClub),
            awayClub = toClubDto(game.awayClub),
        )

    fun toPostBoardResponse(dto: PostBoardResponseDTO): PostBoardResponse =
        PostBoardResponse(
            boardId = dto.boardId,
            title = dto.title,
            content = dto.content,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            bookMarked = dto.bookMarked,
            cheerClub = toClub(dto.cheerClub)!!,
            gameResponse = toGameInfo(dto.gameResponse)!!,
            userResponse = toUserInfo(dto.userResponse),
        )

    private fun toGameInfo(dto: GameInfoDTO?): GameInfo? =
        dto?.let {
            GameInfo(
                gameId = dto.gameId,
                gameStartDate = dto.gameStartDate,
                location = dto.location,
                homeClub = toClub(dto.homeClub),
                awayClub = toClub(dto.awayClub),
            )
        } ?: run {
            null
        }

    private fun toUserInfo(dto: UserInfoDTO): UserInfo =
        UserInfo(
            userId = dto.userId,
            nickName = dto.nickName,
            email = dto.email,
            profileImageUrl = dto.profileImageUrl,
            gender = dto.gender,
            birthDate = dto.birthDate,
            watchStyle = dto.watchStyle,
            club = toClub(dto.club)!!,
        )

    fun toClub(dto: ClubDTO?): Club? =
        dto?.let {
            Club(
                clubId = dto.clubId,
                name = dto.name,
                homeStadium = dto.homeStadium,
                region = dto.region,
            )
        } ?: run {
            null
        }

    fun toClubDto(club: Club?): ClubDTO? =
        club?.let {
            ClubDTO(
                clubId = club.clubId,
                name = club.name,
                homeStadium = club.homeStadium,
                region = club.region,
            )
        } ?: run {
            null
        }

    fun toPutBoardRequestDTO(request: PutBoardRequest): PutBoardRequestDTO =
        PutBoardRequestDTO(
            title = request.title,
            content = request.content,
            maxPerson = request.maxPerson,
            cheerClubId = request.cheerClubId,
            preferredGender = request.preferredGender,
            preferredAgeRange = request.preferredAgeRange,
            completed = request.completed,
            gameRequest = toGameRequestDto(request.gameRequest)!!,
        )

    fun toPutBoardResponse(responseDTO: PutBoardResponseDTO): PutBoardResponse =
        PutBoardResponse(
            boardId = responseDTO.boardId,
            title = responseDTO.title,
            content = responseDTO.content,
            currentPerson = responseDTO.currentPerson,
            maxPerson = responseDTO.maxPerson,
            bookMarked = responseDTO.bookMarked,
            cheerClub = toClub(responseDTO.cheerClub)!!,
            gameResponse = toGameInfo(responseDTO.gameResponse)!!,
            userResponse = toUserInfo(responseDTO.userResponse),
        )

    fun toPatchBoardLiftUpResponse(dto: PatchBoardLiftUpResponseDTO): PatchBoardLiftUpResponse =
        PatchBoardLiftUpResponse(
            state = dto.state,
            remainTime = dto.remainTime,
        )

    fun toGetBoardListResponse(responseDTO: GetBoardListResponseDTO): GetBoardListResponse =
        GetBoardListResponse(
            content = responseDTO.content.map { toBoard(it) },
            pageNumber = responseDTO.pageNumber,
            totalPages = responseDTO.totalPages,
            totalElements = responseDTO.totalElements,
            hasNext = responseDTO.hasNext,
        )

    fun toBoard(dto: BoardDTO): Board =
        Board(
            boardId = dto.boardId,
            title = dto.title,
            content = dto.content,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            bookMarked = dto.bookMarked,
            cheerClub = toClub(dto.cheerClub)!!,
            gameResponse = toGameInfo(dto.gameResponse)!!,
            userResponse = toUserInfo(dto.userResponse),
        )

    fun toGetUserBoardListResponse(dto: GetUserBoardListResponseDTO): GetUserBoardListResponse =
        GetUserBoardListResponse(
            boardInfoList = dto.boardInfoList.map { toBoard(it) },
            totalPages = dto.totalPages,
            totalElements = dto.totalElements,
            isFirst = dto.isFirst,
            isLast = dto.isLast,
        )

    fun toGetBoardResponse(responseDTO: GetBoardResponseDTO): GetBoardResponse =
        GetBoardResponse(
            boardId = responseDTO.boardId,
            title = responseDTO.title,
            content = responseDTO.content,
            currentPerson = responseDTO.currentPerson,
            maxPerson = responseDTO.maxPerson,
            preferredGender = responseDTO.preferredGender,
            preferredAgeRange = responseDTO.preferredAgeRange,
            liftUpDate = responseDTO.liftUpDate,
            bookMarked = responseDTO.bookMarked,
            buttonStatus = responseDTO.buttonStatus,
            chatRoomId = responseDTO.chatRoomId,
            cheerClub = toClub(responseDTO.cheerClub)!!,
            game = toGameInfo(responseDTO.game)!!,
            user = toUserInfo(responseDTO.user),
        )

    fun toGetTempBoardResponse(dto: GetTempBoardResponseDTO): GetTempBoardResponse =
        GetTempBoardResponse(
            boardId = dto.boardId,
            title = dto.title,
            content = dto.content,
            maxPerson = dto.maxPerson,
            preferredGender = dto.preferredGender,
            preferredAgeRange = dto.preferredAgeRange,
            cheerClub = toClub(dto.cheerClub),
            game = toGameInfo(dto.game),
            user = toUserInfo(dto.user),
        )

    fun toGetLikedBoardResponse(responseDTO: GetLikedBoardResponseDTO): GetLikedBoardResponse =
        GetLikedBoardResponse(
            content = responseDTO.content.map { toBoard(it) },
            totalPages = responseDTO.totalPages,
            totalElements = responseDTO.totalElements,
            hasNext = responseDTO.hasNext,
            pageNumber = responseDTO.pageNumber,
        )
}
