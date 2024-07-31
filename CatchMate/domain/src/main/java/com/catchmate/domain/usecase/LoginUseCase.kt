package com.catchmate.domain.usecase

import com.catchmate.domain.model.LoginRequest
import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase
    @Inject
    constructor(
        private val loginRepository: LoginRepository,
    ) {
        suspend fun loginWithKakao(): LoginRequest = loginRepository.loginWithKakao()

        suspend fun loginWithNaver(): LoginRequest = loginRepository.loginWithNaver()

        suspend fun loginWithGoogle(): LoginRequest? = loginRepository.loginWithGoogle()
    }
