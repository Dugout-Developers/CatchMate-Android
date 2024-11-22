package com.catchmate.domain.usecase.auth

import com.catchmate.domain.model.PostLoginRequest
import com.catchmate.domain.model.PostLoginResponse
import com.catchmate.domain.repository.AuthRepository
import javax.inject.Inject

class PostAuthLoginUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend fun postAuthLogin(postLoginRequest: PostLoginRequest): PostLoginResponse? = authRepository.postAuthLogin(postLoginRequest)
    }
