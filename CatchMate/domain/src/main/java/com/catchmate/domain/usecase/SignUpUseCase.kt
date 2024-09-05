package com.catchmate.domain.usecase

import com.catchmate.domain.model.CheckNicknameResponse
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserResponse
import com.catchmate.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase
    @Inject
    constructor(
        private val signUpRepository: SignUpRepository,
    ) {
        suspend fun getNicknameAvailability(nickName: String): CheckNicknameResponse? = signUpRepository.getNicknameAvailability(nickName)

        suspend fun postUserAdditionalInfo(userAdditionalInfoRequest: UserAdditionalInfoRequest): UserResponse? =
            signUpRepository.postUserAdditionalInfo(userAdditionalInfoRequest)
    }
