package com.catchmate.data.mapper

import com.catchmate.data.dto.PostLoginRequestDTO
import com.catchmate.data.dto.PostLoginResponseDTO
import com.catchmate.domain.model.PostLoginRequest
import com.catchmate.domain.model.PostLoginResponse

object AuthMapper {
    fun toPostLoginRequestDTO(postLoginRequest: PostLoginRequest): PostLoginRequestDTO =
        PostLoginRequestDTO(
            providerId = postLoginRequest.providerId,
            provider = postLoginRequest.provider,
            email = postLoginRequest.email,
            picture = postLoginRequest.picture,
            fcmToken = postLoginRequest.fcmToken,
        )

    fun toPostLoginRequest(postLoginRequestDTO: PostLoginRequestDTO): PostLoginRequest =
        PostLoginRequest(
            providerId = postLoginRequestDTO.providerId,
            provider = postLoginRequestDTO.provider,
            email = postLoginRequestDTO.email,
            picture = postLoginRequestDTO.picture,
            fcmToken = postLoginRequestDTO.fcmToken,
        )

    fun toPostLoginResponse(postLoginResponseDTO: PostLoginResponseDTO): PostLoginResponse =
        PostLoginResponse(
            accessToken = postLoginResponseDTO.accessToken,
            refreshToken = postLoginResponseDTO.refreshToken,
            isFirstLogin = postLoginResponseDTO.isFirstLogin,
        )
}
