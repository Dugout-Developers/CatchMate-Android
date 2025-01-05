package com.catchmate.data.mapper

import com.catchmate.data.dto.FavoriteClubDTO
import com.catchmate.data.dto.GetUserProfileByIdResponseDTO
import com.catchmate.data.dto.GetUserProfileResponseDTO
import com.catchmate.data.dto.PatchUserAlarmResponseDTO
import com.catchmate.data.dto.PatchUserProfileRequestDTO
import com.catchmate.data.dto.PatchUserProfileResponseDTO
import com.catchmate.data.dto.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.PostUserAdditionalInfoResponseDTO
import com.catchmate.domain.model.FavoriteClub
import com.catchmate.domain.model.GetUserProfileByIdResponse
import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.model.PatchUserAlarmResponse
import com.catchmate.domain.model.PatchUserProfileRequest
import com.catchmate.domain.model.PatchUserProfileResponse
import com.catchmate.domain.model.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.PostUserAdditionalInfoResponse

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

    fun toPatchUserProfileRequestDTO(request: PatchUserProfileRequest): PatchUserProfileRequestDTO =
        PatchUserProfileRequestDTO(
            nickName = request.nickName,
            description = request.description,
            favGudan = request.favGudan,
            watchStyle = request.watchStyle,
        )

    fun toPatchUserProfileResponse(responseDTO: PatchUserProfileResponseDTO): PatchUserProfileResponse =
        PatchUserProfileResponse(
            userId = responseDTO.userId,
            createdAt = responseDTO.createdAt,
        )

    fun toPatchUserAlarmResponse(responseDTO: PatchUserAlarmResponseDTO): PatchUserAlarmResponse =
        PatchUserAlarmResponse(
            userId = responseDTO.userId,
            pushAgreement = responseDTO.pushAgreement,
            createdAt = responseDTO.createdAt,
        )

    fun toGetUserProfileByIdResponse(responseDTO: GetUserProfileByIdResponseDTO): GetUserProfileByIdResponse =
        GetUserProfileByIdResponse(
            userId = responseDTO.userId,
            email = responseDTO.email,
            picture = responseDTO.picture,
            gender = responseDTO.gender,
            pushAgreement = responseDTO.pushAgreement,
            nickName = responseDTO.nickName,
            favoriteGudan = responseDTO.favoriteGudan,
            birthDate = responseDTO.birthDate,
            watchStyle = responseDTO.watchStyle,
        )
}
