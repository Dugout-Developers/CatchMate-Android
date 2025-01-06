package com.catchmate.data.mapper

import com.catchmate.data.dto.enroll.DeleteEnrollResponseDTO
import com.catchmate.data.dto.enroll.EnrollBoardInfoDTO
import com.catchmate.data.dto.enroll.EnrollInfoDTO
import com.catchmate.data.dto.enroll.EnrollUserInfoDTO
import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.GetAllReceivedEnrollResponseDTO
import com.catchmate.data.dto.enroll.GetEnrollNewCountResponseDTO
import com.catchmate.data.dto.enroll.GetReceivedEnrollResponseDTO
import com.catchmate.data.dto.enroll.GetRequestedEnrollListResponseDTO
import com.catchmate.data.dto.enroll.PatchEnrollAcceptResponseDTO
import com.catchmate.data.dto.enroll.PatchEnrollRejectResponseDTO
import com.catchmate.data.dto.enroll.PostEnrollRequestDTO
import com.catchmate.data.dto.enroll.PostEnrollResponseDTO
import com.catchmate.data.dto.enroll.ReceivedEnrollContentDTO
import com.catchmate.data.dto.user.FavoriteClubDTO
import com.catchmate.domain.model.enroll.DeleteEnrollResponse
import com.catchmate.domain.model.enroll.EnrollBoardInfo
import com.catchmate.domain.model.enroll.EnrollInfo
import com.catchmate.domain.model.enroll.EnrollUserInfo
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.GetAllReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetEnrollNewCountResponse
import com.catchmate.domain.model.enroll.GetReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetRequestedEnrollListResponse
import com.catchmate.domain.model.enroll.PatchEnrollAcceptResponse
import com.catchmate.domain.model.enroll.PatchEnrollRejectResponse
import com.catchmate.domain.model.enroll.PostEnrollRequest
import com.catchmate.domain.model.enroll.PostEnrollResponse
import com.catchmate.domain.model.enroll.ReceivedEnrollContent
import com.catchmate.domain.model.user.FavoriteClub

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
            acceptStatus = responseDTO.acceptStatus,
        )

    fun toPatchEnrollAcceptResponse(responseDTO: PatchEnrollAcceptResponseDTO): PatchEnrollAcceptResponse =
        PatchEnrollAcceptResponse(
            enrollId = responseDTO.enrollId,
            acceptStatus = responseDTO.acceptStatus,
        )

    fun toGetRequestedEnrollListResponse(responseDTO: GetRequestedEnrollListResponseDTO): GetRequestedEnrollListResponse =
        GetRequestedEnrollListResponse(
            enrollInfoList = responseDTO.enrollInfoList.map { toEnrollContent(it) },
        )

    private fun toEnrollContent(dto: EnrollInfoDTO): EnrollInfo =
        EnrollInfo(
            enrollId = dto.enrollId,
            acceptStatus = dto.acceptStatus,
            description = dto.description,
            requestDate = dto.requestDate,
            userInfo = toEnrollUserInfo(dto.userInfo),
            boardInfo = toEnrollBoardInfo(dto.boardInfo),
        )

    private fun toEnrollUserInfo(dto: EnrollUserInfoDTO): EnrollUserInfo =
        EnrollUserInfo(
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

    private fun toEnrollBoardInfo(dto: EnrollBoardInfoDTO): EnrollBoardInfo =
        EnrollBoardInfo(
            boardInfo = dto.boardInfo,
            title = dto.title,
            content = dto.content,
            cheerClubId = dto.cheerClubId,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            preferredGender = dto.preferredGender,
            preferredAgeRange = dto.preferredAgeRange,
            gameInfo = toGameInfo(dto.gameInfo),
            liftUpDate = dto.liftUpDate,
            userInfo = toEnrollUserInfo(dto.userInfo)
        )

    private fun toGameInfo(dto: GameInfoDTO): GameInfo =
        GameInfo(
            homeClubId = dto.homeClubId,
            awayClubId = dto.awayClubId,
            gameStartDate = dto.gameStartDate,
            location = dto.location,
        )

    fun toGetReceivedEnrollResponse(responseDTO: GetReceivedEnrollResponseDTO): GetReceivedEnrollResponse =
        GetReceivedEnrollResponse(
            content = responseDTO.content.map { toReceivedEnrollContent(it) },
        )

    private fun toReceivedEnrollContent(dto: ReceivedEnrollContentDTO): ReceivedEnrollContent =
        ReceivedEnrollContent(
            enrollId = dto.enrollId,
            acceptStatus = dto.acceptStatus,
            description = dto.description,
            requestDate = dto.requestDate,
            userInfo = toEnrollUserInfo(dto.userInfo),
            boardInfo = toEnrollBoardInfo(dto.boardInfo),
            new = dto.new,
        )

    fun toGetAllReceivedEnrollResponse(responseDTO: GetAllReceivedEnrollResponseDTO): GetAllReceivedEnrollResponse =
        GetAllReceivedEnrollResponse(
            content = responseDTO.content.map { toReceivedEnrollContent(it) },
        )

    fun toGetEnrollNewCountResponse(responseDTO: GetEnrollNewCountResponseDTO): GetEnrollNewCountResponse =
        GetEnrollNewCountResponse(
            newEnrollListCount = responseDTO.newEnrollListCount,
        )

    fun toDeleteEnrollResponse(responseDTO: DeleteEnrollResponseDTO): DeleteEnrollResponse =
        DeleteEnrollResponse(
            enrollId = responseDTO.enrollId,
            deleteAt = responseDTO.deleteAt,
        )
}
