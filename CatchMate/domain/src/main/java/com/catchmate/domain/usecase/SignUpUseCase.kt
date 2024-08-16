package com.catchmate.domain.usecase

import com.catchmate.domain.model.CheckNicknameResponse
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserResponse
import com.catchmate.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase
    @Inject
    constructor(
        private val signUpRepository: SignUpRepository
    ) {
        suspend fun getNicknameAvailability(
            accessToken: String,
            nickName: String,
        ): CheckNicknameResponse? = signUpRepository.getNicknameAvailability(accessToken, nickName)

        suspend fun patchUserAdditionalInfo(
            accessToken: String,
            userAdditionalInfoRequest: UserAdditionalInfoRequest,
        ): UserResponse? = signUpRepository.patchUserAdditionalInfo(accessToken, userAdditionalInfoRequest)
    }