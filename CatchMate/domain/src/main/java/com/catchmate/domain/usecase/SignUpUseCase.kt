package com.catchmate.domain.usecase

import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserAdditionalInfoResponse
import com.catchmate.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase
    @Inject
    constructor(
        private val signUpRepository: SignUpRepository,
    ) {
        suspend fun postUserAdditionalInfo(userAdditionalInfoRequest: UserAdditionalInfoRequest): UserAdditionalInfoResponse? =
            signUpRepository.postUserAdditionalInfo(userAdditionalInfoRequest)
    }
