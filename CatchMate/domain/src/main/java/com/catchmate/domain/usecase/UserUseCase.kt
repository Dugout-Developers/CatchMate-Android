package com.catchmate.domain.usecase

import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        suspend fun getUserProfile(): GetUserProfileResponse? = userRepository.getUserProfile()
    }
