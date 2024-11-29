package com.catchmate.data.mapper

import com.catchmate.data.dto.GetUserProfileResponseDTO
import com.catchmate.data.dto.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.PostUserAdditionalInfoResponseDTO
import com.catchmate.domain.model.GetUserProfileResponse
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
}
