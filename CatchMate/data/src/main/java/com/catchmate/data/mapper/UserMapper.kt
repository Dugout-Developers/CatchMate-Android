package com.catchmate.data.mapper

import com.catchmate.data.dto.GetUserProfileResponseDTO
import com.catchmate.domain.model.GetUserProfileResponse

object UserMapper {
    fun toUserProfileResponse(userProfileResponseDTO: GetUserProfileResponseDTO): GetUserProfileResponse =
        GetUserProfileResponse(
            userId = userProfileResponseDTO.userId,
            email = userProfileResponseDTO.email,
            picture = userProfileResponseDTO.picture,
            gender = userProfileResponseDTO.gender,
            pushAgreement = userProfileResponseDTO.pushAgreement,
            nickName = userProfileResponseDTO.nickName,
            favoriteGudan = userProfileResponseDTO.favoriteGudan,
            description = userProfileResponseDTO.description,
            birthDate = userProfileResponseDTO.birthDate,
            watchStyle = userProfileResponseDTO.watchStyle,
        )
}
