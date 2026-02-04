package com.catchmate.data.mapper

import android.R
import com.catchmate.data.dto.board.BoardDTO
import com.catchmate.data.dto.enroll.AllReceivedEnrollInfoResponseDTO
import com.catchmate.data.dto.enroll.DeleteEnrollResponseDTO
import com.catchmate.data.dto.enroll.EnrollBoardInfoDTO
import com.catchmate.data.dto.enroll.EnrollInfoDTO
import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.GetAllReceivedEnrollResponseDTO
import com.catchmate.data.dto.enroll.GetEnrollNewCountResponseDTO
import com.catchmate.data.dto.enroll.GetReceivedEnrollResponseDTO
import com.catchmate.data.dto.enroll.GetRequestedEnrollListResponseDTO
import com.catchmate.data.dto.enroll.GetEnrollResponseDTO
import com.catchmate.data.dto.enroll.PatchEnrollAcceptResponseDTO
import com.catchmate.data.dto.enroll.PatchEnrollRejectResponseDTO
import com.catchmate.data.dto.enroll.PostEnrollRequestDTO
import com.catchmate.data.dto.enroll.PostEnrollResponseDTO
import com.catchmate.data.dto.enroll.ReceivedEnrollInfoDTO
import com.catchmate.data.dto.enroll.ReceivedEnrollInfoResponseDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.mapper.BoardMapper.toClub
import com.catchmate.domain.model.board.Board
import com.catchmate.domain.model.enroll.AllReceivedEnrollInfoResponse
import com.catchmate.domain.model.enroll.DeleteEnrollResponse
import com.catchmate.domain.model.enroll.EnrollBoardInfo
import com.catchmate.domain.model.enroll.EnrollInfo
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.GetAllReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetEnrollNewCountResponse
import com.catchmate.domain.model.enroll.GetReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetRequestedEnrollListResponse
import com.catchmate.domain.model.enroll.GetEnrollResponse
import com.catchmate.domain.model.enroll.PatchEnrollAcceptResponse
import com.catchmate.domain.model.enroll.PatchEnrollRejectResponse
import com.catchmate.domain.model.enroll.PostEnrollRequest
import com.catchmate.domain.model.enroll.PostEnrollResponse
import com.catchmate.domain.model.enroll.ReceivedEnrollInfo
import com.catchmate.domain.model.enroll.ReceivedEnrollInfoResponse
import com.catchmate.domain.model.enroll.UserInfo

object EnrollMapper {
    fun toPostEnrollRequestDTO(request: PostEnrollRequest): PostEnrollRequestDTO =
        PostEnrollRequestDTO(
            description = request.description,
        )

    fun toPostEnrollResponse(responseDTO: PostEnrollResponseDTO): PostEnrollResponse =
        PostEnrollResponse(
            enrollId = responseDTO.enrollId,
            requestAt = responseDTO.requestAt,
        )

    fun toPatchEnrollRejectResponse(responseDTO: PatchEnrollRejectResponseDTO): PatchEnrollRejectResponse =
        PatchEnrollRejectResponse(
            enrollId = responseDTO.enrollId,
            message = responseDTO.message,
        )

    fun toPatchEnrollAcceptResponse(responseDTO: PatchEnrollAcceptResponseDTO): PatchEnrollAcceptResponse =
        PatchEnrollAcceptResponse(
            enrollId = responseDTO.enrollId,
            message = responseDTO.message,
        )

    fun toGetEnrollResponse(dto: GetEnrollResponseDTO): GetEnrollResponse =
        GetEnrollResponse(
            enrollId = dto.enrollId,
            acceptStatus = dto.acceptStatus,
            description = dto.description,
            requestDate = dto.requestDate,
            applicant = toEnrollUserInfo(dto.applicant),
            boardResponse = toEnrollBoardResponse(dto.boardResponse),
        )

    private fun toEnrollBoardResponse(dto: BoardDTO): Board =
        Board(
            dto.boardId,
            dto.title,
            dto.content,
            dto.currentPerson,
            dto.maxPerson,
            dto.bookMarked,
            toClub(dto.cheerClub)!!,
            toGameInfo(dto.gameResponse),
            toEnrollUserInfo(dto.userResponse),
        )

    fun toGetRequestedEnrollListResponse(responseDTO: GetRequestedEnrollListResponseDTO): GetRequestedEnrollListResponse =
        GetRequestedEnrollListResponse(
            content = responseDTO.content.map { toEnrollInfo(it) },
            pageNumber = responseDTO.pageNumber,
            totalPages = responseDTO.totalPages,
            totalElements = responseDTO.totalElements,
            hasNext = responseDTO.hasNext,
        )

    private fun toEnrollInfo(dto: EnrollInfoDTO): EnrollInfo =
        EnrollInfo(
            enrollId = dto.enrollId,
            acceptStatus = dto.acceptStatus,
            description = dto.description,
            requestDate = dto.requestDate,
            boardResponse = toEnrollBoardInfo(dto.boardResponse),
        )

    private fun toEnrollUserInfo(dto: UserInfoDTO): UserInfo =
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

    private fun toEnrollBoardInfo(dto: EnrollBoardInfoDTO): EnrollBoardInfo =
        EnrollBoardInfo(
            boardId = dto.boardId,
            title = dto.title,
            content = dto.content,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            bookMarked = dto.bookMarked,
            cheerClub = toClub(dto.cheerClub)!!,
            gameResponse = toGameInfo(dto.gameResponse),
            userResponse = toEnrollUserInfo(dto.userResponse),
        )

    private fun toGameInfo(dto: GameInfoDTO): GameInfo =
        GameInfo(
            gameId = dto.gameId,
            gameStartDate = dto.gameStartDate,
            location = dto.location,
            homeClub = toClub(dto.homeClub),
            awayClub = toClub(dto.awayClub),
        )

    fun toGetReceivedEnrollResponse(responseDTO: GetReceivedEnrollResponseDTO): GetReceivedEnrollResponse =
        GetReceivedEnrollResponse(
            enrollInfoList = responseDTO.enrollInfoList.map { toReceivedEnrollInfoResponse(it) },
            totalPages = responseDTO.totalPages,
            totalElements = responseDTO.totalElements,
            isFirst = responseDTO.isFirst,
            isLast = responseDTO.isLast,
        )

    private fun toReceivedEnrollInfoResponse(dto: ReceivedEnrollInfoResponseDTO): ReceivedEnrollInfoResponse =
        ReceivedEnrollInfoResponse(
            enrollReceiveInfoList = dto.enrollReceiveInfoList.map { toReceivedEnrollInfo(it) },
        )

    private fun toReceivedEnrollInfo(dto: ReceivedEnrollInfoDTO): ReceivedEnrollInfo =
        ReceivedEnrollInfo(
            enrollId = dto.enrollId,
            acceptStatus = dto.acceptStatus,
            description = dto.description,
            requestDate = dto.requestDate,
            userInfo = toEnrollUserInfo(dto.userInfo),
            new = dto.new,
        )

    fun toGetAllReceivedEnrollResponse(responseDTO: GetAllReceivedEnrollResponseDTO): GetAllReceivedEnrollResponse =
        GetAllReceivedEnrollResponse(
            enrollInfoList = responseDTO.enrollInfoList.map { toAllReceivedEnrollInfoResponse(it) },
            totalPages = responseDTO.totalPages,
            totalElements = responseDTO.totalElements,
            isFirst = responseDTO.isFirst,
            isLast = responseDTO.isLast,
        )

    private fun toAllReceivedEnrollInfoResponse(dto: AllReceivedEnrollInfoResponseDTO): AllReceivedEnrollInfoResponse =
        AllReceivedEnrollInfoResponse(
            boardInfo = toEnrollBoardInfo(dto.boardInfo),
            enrollReceiveInfoList = dto.enrollReceiveInfoList.map { toReceivedEnrollInfo(it) },
        )

    fun toGetEnrollNewCountResponse(responseDTO: GetEnrollNewCountResponseDTO): GetEnrollNewCountResponse =
        GetEnrollNewCountResponse(
            newEnrollCount = responseDTO.newEnrollCount,
        )

    fun toDeleteEnrollResponse(responseDTO: DeleteEnrollResponseDTO): DeleteEnrollResponse =
        DeleteEnrollResponse(
            enrollId = responseDTO.enrollId,
            deletedAt = responseDTO.deletedAt,
        )
}
