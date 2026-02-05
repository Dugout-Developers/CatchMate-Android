package com.catchmate.data.mapper

import com.catchmate.data.dto.user.BlockedUserInfoDto
import com.catchmate.data.dto.user.DeleteBlockedUserResponseDTO
import com.catchmate.data.dto.user.DeleteUserAccountResponseDTO
import com.catchmate.data.dto.user.GetBlockedUserListResponseDTO
import com.catchmate.data.dto.user.GetCheckNicknameResponseDTO
import com.catchmate.data.dto.user.GetUserProfileByIdResponseDTO
import com.catchmate.data.dto.user.GetUserProfileResponseDTO
import com.catchmate.data.dto.user.PatchUserAlarmResponseDTO
import com.catchmate.data.dto.user.PatchUserProfileResponseDTO
import com.catchmate.data.dto.user.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.user.PostUserAdditionalInfoResponseDTO
import com.catchmate.data.dto.user.PostUserBlockResponseDTO
import com.catchmate.data.mapper.BoardMapper.toClub
import com.catchmate.domain.model.user.BlockedUserInfo
import com.catchmate.domain.model.user.DeleteBlockedUserResponse
import com.catchmate.domain.model.user.DeleteUserAccountResponse
import com.catchmate.domain.model.user.GetBlockedUserListResponse
import com.catchmate.domain.model.user.GetCheckNicknameResponse
import com.catchmate.domain.model.user.GetUserProfileByIdResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.domain.model.user.PatchUserAlarmResponse
import com.catchmate.domain.model.user.PatchUserProfileResponse
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.user.PostUserAdditionalInfoResponse
import com.catchmate.domain.model.user.PostUserBlockResponse

object UserMapper {
    fun toGetUserProfileResponse(getUserProfileResponseDTO: GetUserProfileResponseDTO): GetUserProfileResponse =
        GetUserProfileResponse(
            userId = getUserProfileResponseDTO.userId,
            nickName = getUserProfileResponseDTO.nickName,
            email = getUserProfileResponseDTO.email,
            profileImageUrl = getUserProfileResponseDTO.profileImageUrl,
            gender = getUserProfileResponseDTO.gender,
            birthDate = getUserProfileResponseDTO.birthDate,
            watchStyle = getUserProfileResponseDTO.watchStyle,
            club = toClub(getUserProfileResponseDTO.club)!!,
        )

    fun toPostUserAdditionalInfoRequestDTO(request: PostUserAdditionalInfoRequest): PostUserAdditionalInfoRequestDTO =
        PostUserAdditionalInfoRequestDTO(
            email = request.email,
            providerId = request.providerId,
            provider = request.provider,
            profileImageUrl = request.profileImageUrl,
            fcmToken = request.fcmToken,
            gender = request.gender,
            nickName = request.nickName,
            birthDate = request.birthDate,
            favoriteClubId = request.favoriteClubId,
            watchStyle = request.watchStyle,
        )

    fun toPostUserAdditionalInfoResponse(responseDTO: PostUserAdditionalInfoResponseDTO): PostUserAdditionalInfoResponse =
        PostUserAdditionalInfoResponse(
            userId = responseDTO.userId,
            accessToken = responseDTO.accessToken,
            refreshToken = responseDTO.refreshToken,
            createdAt = responseDTO.createdAt,
        )

    fun toPatchUserProfileResponse(responseDTO: PatchUserProfileResponseDTO): PatchUserProfileResponse =
        PatchUserProfileResponse(
            userId = responseDTO.userId,
            email = responseDTO.email,
            profileImageUrl = responseDTO.profileImageUrl,
            gender = responseDTO.gender,
            allAlarm = responseDTO.allAlarm,
            chatAlarm = responseDTO.chatAlarm,
            enrollAlarm = responseDTO.enrollAlarm,
            eventAlarm = responseDTO.eventAlarm,
            nickName = responseDTO.nickName,
            club = toClub(responseDTO.club)!!,
            birthDate = responseDTO.birthDate,
            watchStyle = responseDTO.watchStyle,
        )

    fun toPatchUserAlarmResponse(responseDTO: PatchUserAlarmResponseDTO): PatchUserAlarmResponse =
        PatchUserAlarmResponse(
            alarmType = responseDTO.alarmType,
            enabled = responseDTO.enabled,
        )

    fun toGetCheckNicknameResponse(getCheckNicknameResponseDTO: GetCheckNicknameResponseDTO): GetCheckNicknameResponse =
        GetCheckNicknameResponse(
            nickName = getCheckNicknameResponseDTO.nickName,
            available = getCheckNicknameResponseDTO.available,
        )

    fun toGetUserProfileByIdResponse(responseDTO: GetUserProfileByIdResponseDTO): GetUserProfileByIdResponse =
        GetUserProfileByIdResponse(
            userId = responseDTO.userId,
            nickName = responseDTO.nickName,
            email = responseDTO.email,
            profileImageUrl = responseDTO.profileImageUrl,
            gender = responseDTO.gender,
            birthDate = responseDTO.birthDate,
            watchStyle = responseDTO.watchStyle,
            club = toClub(responseDTO.club)!!,
        )

    fun toGetBlockedUserListResponse(dto: GetBlockedUserListResponseDTO): GetBlockedUserListResponse =
        GetBlockedUserListResponse(
            content = dto.content.map { toBlockedUserInfo(it) },
            pageNumber = dto.pageNumber,
            totalPages = dto.totalPages,
            totalElements = dto.totalElements,
            hasNext = dto.hasNext,
        )

    private fun toBlockedUserInfo(dto: BlockedUserInfoDto): BlockedUserInfo =
        BlockedUserInfo(
            blockId = dto.blockId,
            userId = dto.userId,
            nickName = dto.nickName,
            profileImageUrl = dto.profileImageUrl,
            blockedAt = dto.blockedAt,
        )

    fun toDeleteBlockedUserResponse(dto: DeleteBlockedUserResponseDTO): DeleteBlockedUserResponse =
        DeleteBlockedUserResponse(
            targetUserId = dto.targetUserId,
            message = dto.message,
        )

    fun toPostUserBlockResponse(dto: PostUserBlockResponseDTO): PostUserBlockResponse =
        PostUserBlockResponse(
            targetUserId = dto.targetUserId,
            message = dto.message,
        )

    fun toDeleteUserAccountResponse(dto: DeleteUserAccountResponseDTO): DeleteUserAccountResponse =
        DeleteUserAccountResponse(
            state = dto.state,
        )
}
