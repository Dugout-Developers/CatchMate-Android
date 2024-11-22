package com.catchmate.data.mapper

import com.catchmate.data.dto.UserAdditionalInfoRequestDTO
import com.catchmate.data.dto.UserAdditionalInfoResponseDTO
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserAdditionalInfoResponse

object SignUpMapper {
    fun toUserAdditionalInfoRequestDTO(userAdditionalInfoRequest: UserAdditionalInfoRequest): UserAdditionalInfoRequestDTO =
        UserAdditionalInfoRequestDTO(
            email = userAdditionalInfoRequest.email,
            provider = userAdditionalInfoRequest.provider,
            providerId = userAdditionalInfoRequest.providerId,
            gender = userAdditionalInfoRequest.gender,
            picture = userAdditionalInfoRequest.picture,
            fcmToken = userAdditionalInfoRequest.fcmToken,
            nickName = userAdditionalInfoRequest.nickName,
            birthDate = userAdditionalInfoRequest.birthDate,
            favGudan = userAdditionalInfoRequest.favGudan,
            watchStyle = userAdditionalInfoRequest.watchStyle,
        )

    fun toUserAdditionalInfoRequest(userAdditionalInfoRequestDTO: UserAdditionalInfoRequestDTO): UserAdditionalInfoRequest =
        UserAdditionalInfoRequest(
            email = userAdditionalInfoRequestDTO.email,
            provider = userAdditionalInfoRequestDTO.provider,
            providerId = userAdditionalInfoRequestDTO.providerId,
            gender = userAdditionalInfoRequestDTO.gender,
            picture = userAdditionalInfoRequestDTO.picture,
            fcmToken = userAdditionalInfoRequestDTO.fcmToken,
            nickName = userAdditionalInfoRequestDTO.nickName,
            birthDate = userAdditionalInfoRequestDTO.birthDate,
            favGudan = userAdditionalInfoRequestDTO.favGudan,
            watchStyle = userAdditionalInfoRequestDTO.watchStyle,
        )

    fun toUserAdditionalInfoResponse(userAdditionalInfoResponseDTO: UserAdditionalInfoResponseDTO): UserAdditionalInfoResponse =
        UserAdditionalInfoResponse(
            accessToken = userAdditionalInfoResponseDTO.accessToken,
            refreshToken = userAdditionalInfoResponseDTO.refreshToken,
            userId = userAdditionalInfoResponseDTO.userId,
            createdAt = userAdditionalInfoResponseDTO.createdAt,
        )
}
