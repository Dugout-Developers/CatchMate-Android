package com.catchmate.data.mapper

import com.catchmate.data.dto.auth.DeleteLogoutResponseDTO
import com.catchmate.data.dto.auth.GetCheckNicknameResponseDTO
import com.catchmate.data.dto.auth.PostLoginRequestDTO
import com.catchmate.data.dto.auth.PostLoginResponseDTO
import com.catchmate.data.dto.auth.UserDataDTO
import com.catchmate.domain.model.auth.DeleteLogoutResponse
import com.catchmate.domain.model.auth.GetCheckNicknameResponse
import com.catchmate.domain.model.auth.PostLoginRequest
import com.catchmate.domain.model.auth.PostLoginResponse
import com.catchmate.domain.model.auth.UserData

object AuthMapper {
    fun toPostLoginRequestDTO(postLoginRequest: PostLoginRequest): PostLoginRequestDTO =
        PostLoginRequestDTO(
            providerId = postLoginRequest.providerId,
            provider = postLoginRequest.provider,
            fcmToken = postLoginRequest.fcmToken,
        )

    fun toPostLoginRequest(postLoginRequestDTO: PostLoginRequestDTO?): PostLoginRequest? =
        if (postLoginRequestDTO == null) {
            null
        } else {
            PostLoginRequest(
                providerId = postLoginRequestDTO.providerId,
                provider = postLoginRequestDTO.provider,
                fcmToken = postLoginRequestDTO.fcmToken,
            )
        }

    fun toUserData(userDataDTO: UserDataDTO?): UserData? =
        if (userDataDTO == null) {
            null
        } else {
            UserData(
                email = userDataDTO.email,
                profileImageUrl = userDataDTO.profileImageUrl,
                providerId = userDataDTO.providerId,
                provider = userDataDTO.provider,
                fcmToken = userDataDTO.fcmToken,
            )
        }

    fun toGoogleUserData(userDataDTO: UserDataDTO): UserData =
        UserData(
            email = userDataDTO.email,
            profileImageUrl = userDataDTO.profileImageUrl,
            providerId = userDataDTO.providerId,
            provider = userDataDTO.provider,
            fcmToken = userDataDTO.fcmToken,
        )

    fun toPostLoginResponse(postLoginResponseDTO: PostLoginResponseDTO): PostLoginResponse =
        PostLoginResponse(
            accessToken = postLoginResponseDTO.accessToken,
            refreshToken = postLoginResponseDTO.refreshToken,
            signupRequired = postLoginResponseDTO.signupRequired,
        )

    fun toGetCheckNicknameResponse(getCheckNicknameResponseDTO: GetCheckNicknameResponseDTO): GetCheckNicknameResponse =
        GetCheckNicknameResponse(
            available = getCheckNicknameResponseDTO.available,
        )

    fun toDeleteLogoutResponse(responseDTO: DeleteLogoutResponseDTO): DeleteLogoutResponse =
        DeleteLogoutResponse(
            state = responseDTO.state,
        )
}
