package com.catchmate.data.mapper

import com.catchmate.data.dto.EnrollBoardInfoDTO
import com.catchmate.data.dto.EnrollContentDTO
import com.catchmate.data.dto.EnrollUserInfoDTO
import com.catchmate.data.dto.GetAllReceivedEnrollResponseDTO
import com.catchmate.data.dto.GetReceivedEnrollResponseDTO
import com.catchmate.data.dto.GetRequestedEnrollListResponseDTO
import com.catchmate.data.dto.PatchEnrollAcceptResponseDTO
import com.catchmate.data.dto.PatchEnrollRejectResponseDTO
import com.catchmate.data.dto.PostEnrollRequestDTO
import com.catchmate.data.dto.PostEnrollResponseDTO
import com.catchmate.data.dto.ReceivedEnrollContentDTO
import com.catchmate.domain.model.EnrollBoardInfo
import com.catchmate.domain.model.EnrollContent
import com.catchmate.domain.model.EnrollUserInfo
import com.catchmate.domain.model.GetAllReceivedEnrollResponse
import com.catchmate.domain.model.GetReceivedEnrollResponse
import com.catchmate.domain.model.GetRequestedEnrollListResponse
import com.catchmate.domain.model.PatchEnrollAcceptResponse
import com.catchmate.domain.model.PatchEnrollRejectResponse
import com.catchmate.domain.model.PostEnrollRequest
import com.catchmate.domain.model.PostEnrollResponse
import com.catchmate.domain.model.ReceivedEnrollContent

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
            content = responseDTO.content.map { toEnrollContent(it) }
        )

    private fun toEnrollContent(dto: EnrollContentDTO): EnrollContent =
        EnrollContent(
            enrollId = dto.enrollId,
            acceptStatus = dto.acceptStatus,
            description = dto.description,
            userInfo = toEnrollUserInfo(dto.userInfo),
            boardInfo = toEnrollBoardInfo(dto.boardInfo),
        )

    private fun toEnrollUserInfo(dto: EnrollUserInfoDTO): EnrollUserInfo =
        EnrollUserInfo(
            userId = dto.userId,
            nickName = dto.nickName,
            picture = dto.picture,
            favGudan = dto.favGudan,
            watchStyle = dto.watchStyle,
            gender = dto.gender,
            birthDate = dto.birthDate,
        )

    private fun toEnrollBoardInfo(dto: EnrollBoardInfoDTO): EnrollBoardInfo =
        EnrollBoardInfo(
            boardInfo = dto.boardInfo,
            title = dto.title,
            gameDate = dto.gameDate,
            location = dto.location,
            homeTeam = dto.homeTeam,
            awayTeam = dto.awayTeam,
            cheerTeam = dto.cheerTeam,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            addInfo = dto.addInfo,
        )

    fun toGetReceivedEnrollResponse(responseDTO: GetReceivedEnrollResponseDTO): GetReceivedEnrollResponse =
        GetReceivedEnrollResponse(
            content = responseDTO.content.map { toReceivedEnrollContent(it) }
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
            content = responseDTO.content.map { toReceivedEnrollContent(it) }
        )
}
