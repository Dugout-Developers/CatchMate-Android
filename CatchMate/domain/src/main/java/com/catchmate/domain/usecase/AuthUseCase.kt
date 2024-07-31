package com.catchmate.domain.usecase

import com.catchmate.domain.model.LoginRequest
import com.catchmate.domain.model.LoginResponse
import com.catchmate.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend fun postLogin(loginRequest: LoginRequest): LoginResponse? = authRepository.postLogin(loginRequest)
    }
