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
            gender = userAdditionalInfoRequest.gender,
            nickName = userAdditionalInfoRequest.nickName,
            birthDate = userAdditionalInfoRequest.birthDate,
            favGudan = userAdditionalInfoRequest.favGudan,
            watchStyle = userAdditionalInfoRequest.watchStyle,
        )

    fun toUserAdditionalInfoRequest(userAdditionalInfoRequestDTO: UserAdditionalInfoRequestDTO): UserAdditionalInfoRequest =
        UserAdditionalInfoRequest(
            gender = userAdditionalInfoRequestDTO.gender,
            nickName = userAdditionalInfoRequestDTO.nickName,
            birthDate = userAdditionalInfoRequestDTO.birthDate,
            favGudan = userAdditionalInfoRequestDTO.favGudan,
            watchStyle = userAdditionalInfoRequestDTO.watchStyle,
        )

    fun toUserResponse(userResponseDTO: UserResponseDTO): UserResponse =
        UserResponse(
            userId = userResponseDTO.userId,
            createdAt = userResponseDTO.createdAt,
        )
}
