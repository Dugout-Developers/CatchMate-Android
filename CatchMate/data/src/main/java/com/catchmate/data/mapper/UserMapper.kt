package com.catchmate.data.mapper

import com.catchmate.data.dto.GetUserProfileByIdResponseDTO
import com.catchmate.data.dto.GetUserProfileResponseDTO
import com.catchmate.data.dto.PatchUserAlarmResponseDTO
import com.catchmate.data.dto.PatchUserProfileRequestDTO
import com.catchmate.data.dto.PatchUserProfileResponseDTO
import com.catchmate.data.dto.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.PostUserAdditionalInfoResponseDTO
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
            picture = getUserProfileResponseDTO.picture,
            gender = getUserProfileResponseDTO.gender,
            pushAgreement = getUserProfileResponseDTO.pushAgreement,
            nickName = getUserProfileResponseDTO.nickName,
            favoriteGudan = getUserProfileResponseDTO.favoriteGudan,
            description = getUserProfileResponseDTO.description,
            birthDate = getUserProfileResponseDTO.birthDate,
            watchStyle = getUserProfileResponseDTO.watchStyle,
        )

    fun toPostUserAdditionalInfoRequestDTO(request: PostUserAdditionalInfoRequest): PostUserAdditionalInfoRequestDTO =
        PostUserAdditionalInfoRequestDTO(
            email = request.email,
            provider = request.provider,
            providerId = request.providerId,
            gender = request.gender,
            picture = request.picture,
            fcmToken = request.fcmToken,
            nickName = request.nickName,
            birthDate = request.birthDate,
            favGudan = request.favGudan,
            watchStyle = request.watchStyle,
        )

    fun toPostUserAdditionalInfoResponse(responseDTO: PostUserAdditionalInfoResponseDTO): PostUserAdditionalInfoResponse =
        PostUserAdditionalInfoResponse(
            accessToken = responseDTO.accessToken,
            refreshToken = responseDTO.refreshToken,
            userId = responseDTO.userId,
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
