package com.catchmate.data.mapper

import com.catchmate.data.dto.GetUserProfileResponseDTO
import com.catchmate.domain.model.GetUserProfileResponse

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
}
