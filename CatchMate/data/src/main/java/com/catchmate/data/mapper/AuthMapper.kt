package com.catchmate.data.mapper

import com.catchmate.data.dto.LoginRequestDTO
import com.catchmate.data.dto.LoginResponseDTO
import com.catchmate.domain.model.LoginRequest
import com.catchmate.domain.model.LoginResponse

object AuthMapper {
    fun toLoginRequestDTO(loginRequest: LoginRequest): LoginRequestDTO =
        LoginRequestDTO(
            providerId = loginRequest.providerId,
            provider = loginRequest.provider,
            email = loginRequest.email,
            picture = loginRequest.picture,
            fcmToken = loginRequest.fcmToken,
        )

    fun toLoginRequest(loginRequestDTO: LoginRequestDTO): LoginRequest =
        LoginRequest(
            providerId = loginRequestDTO.providerId,
            provider = loginRequestDTO.provider,
            email = loginRequestDTO.email,
            picture = loginRequestDTO.picture,
            fcmToken = loginRequestDTO.fcmToken,
        )

    fun toLoginResponse(loginResponseDTO: LoginResponseDTO): LoginResponse =
        LoginResponse(
            accessToken = loginResponseDTO.accessToken,
            refreshToken = loginResponseDTO.refreshToken,
            isFirstLogin = loginResponseDTO.isFirstLogin,
        )
}
