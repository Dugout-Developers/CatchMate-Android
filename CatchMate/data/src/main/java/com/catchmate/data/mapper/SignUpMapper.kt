package com.catchmate.data.mapper

import com.catchmate.data.dto.CheckNicknameResponseDTO
import com.catchmate.data.dto.UserAdditionalInfoRequestDTO
import com.catchmate.data.dto.UserResponseDTO
import com.catchmate.domain.model.CheckNicknameResponse
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserResponse

object SignUpMapper {
    fun toCheckNicknameResponse(checkNicknameResponseDTO: CheckNicknameResponseDTO): CheckNicknameResponse =
        CheckNicknameResponse(
            available = checkNicknameResponseDTO.available,
        )

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

    fun toUserResponse(userResponseDTO: UserResponseDTO): UserResponse =
        UserResponse(
            accessToken = userResponseDTO.accessToken,
            refreshToken = userResponseDTO.refreshToken,
            userId = userResponseDTO.userId,
            createdAt = userResponseDTO.createdAt,
        )
}
