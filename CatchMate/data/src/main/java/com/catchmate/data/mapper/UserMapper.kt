package com.catchmate.data.mapper

import com.catchmate.data.dto.user.FavoriteClubDTO
import com.catchmate.data.dto.user.GetBlockedUserListResponseDTO
import com.catchmate.data.dto.user.GetUserProfileByIdResponseDTO
import com.catchmate.data.dto.user.GetUserProfileResponseDTO
import com.catchmate.data.dto.user.PatchUserAlarmResponseDTO
import com.catchmate.data.dto.user.PatchUserProfileResponseDTO
import com.catchmate.data.dto.user.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.user.PostUserAdditionalInfoResponseDTO
import com.catchmate.domain.model.user.FavoriteClub
import com.catchmate.domain.model.user.GetBlockedUserListResponse
import com.catchmate.domain.model.user.GetUserProfileByIdResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.domain.model.user.PatchUserAlarmResponse
import com.catchmate.domain.model.user.PatchUserProfileResponse
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.user.PostUserAdditionalInfoResponse

object UserMapper {
    fun toGetUserProfileResponse(getUserProfileResponseDTO: GetUserProfileResponseDTO): GetUserProfileResponse =
        GetUserProfileResponse(
            userId = getUserProfileResponseDTO.userId,
            email = getUserProfileResponseDTO.email,
            profileImageUrl = getUserProfileResponseDTO.profileImageUrl,
            gender = getUserProfileResponseDTO.gender,
            allAlarm = getUserProfileResponseDTO.allAlarm,
            chatAlarm = getUserProfileResponseDTO.chatAlarm,
            enrollAlarm = getUserProfileResponseDTO.enrollAlarm,
            eventAlarm = getUserProfileResponseDTO.eventAlarm,
            nickName = getUserProfileResponseDTO.nickName,
            favoriteClub = toFavoriteClub(getUserProfileResponseDTO.favoriteClub),
            birthDate = getUserProfileResponseDTO.birthDate,
            watchStyle = getUserProfileResponseDTO.watchStyle,
        )

    private fun toFavoriteClub(dto: FavoriteClubDTO): FavoriteClub =
        FavoriteClub(
            dto.id,
            dto.name,
            dto.homeStadium,
            dto.region,
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
            state = responseDTO.state,
        )

    fun toPatchUserAlarmResponse(responseDTO: PatchUserAlarmResponseDTO): PatchUserAlarmResponse =
        PatchUserAlarmResponse(
            userId = responseDTO.userId,
            alarmType = responseDTO.alarmType,
            isEnabled = responseDTO.isEnabled,
            createdAt = responseDTO.createdAt,
        )

    fun toGetUserProfileByIdResponse(responseDTO: GetUserProfileByIdResponseDTO): GetUserProfileByIdResponse =
        GetUserProfileByIdResponse(
            userId = responseDTO.userId,
            email = responseDTO.email,
            profileImageUrl = responseDTO.profileImageUrl,
            gender = responseDTO.gender,
            allAlarm = responseDTO.allAlarm,
            chatAlarm = responseDTO.chatAlarm,
            enrollAlarm = responseDTO.enrollAlarm,
            eventAlarm = responseDTO.eventAlarm,
            nickName = responseDTO.nickName,
            favoriteClub = toFavoriteClub(responseDTO.favoriteClub),
            birthDate = responseDTO.birthDate,
            watchStyle = responseDTO.watchStyle,
        )

    fun toGetBlockedUserListResponse(dto: GetBlockedUserListResponseDTO): GetBlockedUserListResponse =
        GetBlockedUserListResponse(
            userInfoList = dto.userInfoList.map { toGetUserProfileResponse(it) },
            totalPages = dto.totalPages,
            totalElements = dto.totalElements,
            isFirst = dto.isFirst,
            isLast = dto.isLast,
        )
}
