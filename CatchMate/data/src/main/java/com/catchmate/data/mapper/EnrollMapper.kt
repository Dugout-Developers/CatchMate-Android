package com.catchmate.data.mapper

import com.catchmate.data.dto.enroll.DeleteEnrollResponseDTO
import com.catchmate.data.dto.enroll.EnrollBoardInfoDTO
import com.catchmate.data.dto.enroll.EnrollContentDTO
import com.catchmate.data.dto.enroll.EnrollUserInfoDTO
import com.catchmate.data.dto.enroll.GetAllReceivedEnrollResponseDTO
import com.catchmate.data.dto.enroll.GetEnrollNewCountResponseDTO
import com.catchmate.data.dto.enroll.GetReceivedEnrollResponseDTO
import com.catchmate.data.dto.enroll.GetRequestedEnrollListResponseDTO
import com.catchmate.data.dto.enroll.PatchEnrollAcceptResponseDTO
import com.catchmate.data.dto.enroll.PatchEnrollRejectResponseDTO
import com.catchmate.data.dto.enroll.PostEnrollRequestDTO
import com.catchmate.data.dto.enroll.PostEnrollResponseDTO
import com.catchmate.data.dto.enroll.ReceivedEnrollContentDTO
import com.catchmate.domain.model.enroll.DeleteEnrollResponse
import com.catchmate.domain.model.enroll.EnrollBoardInfo
import com.catchmate.domain.model.enroll.EnrollContent
import com.catchmate.domain.model.enroll.EnrollUserInfo
import com.catchmate.domain.model.enroll.GetAllReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetEnrollNewCountResponse
import com.catchmate.domain.model.enroll.GetReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetRequestedEnrollListResponse
import com.catchmate.domain.model.enroll.PatchEnrollAcceptResponse
import com.catchmate.domain.model.enroll.PatchEnrollRejectResponse
import com.catchmate.domain.model.enroll.PostEnrollRequest
import com.catchmate.domain.model.enroll.PostEnrollResponse
import com.catchmate.domain.model.enroll.ReceivedEnrollContent

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
            content = responseDTO.content.map { toEnrollContent(it) },
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
