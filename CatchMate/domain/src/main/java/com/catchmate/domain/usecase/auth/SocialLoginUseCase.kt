package com.catchmate.domain.usecase.auth

import android.app.Activity
import com.catchmate.domain.model.auth.PostLoginRequest
import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class SocialLoginUseCase
    @Inject
    constructor(
        private val loginRepository: LoginRepository,
    ) {
        suspend fun loginWithKakao(): PostLoginRequest? = loginRepository.loginWithKakao()

        suspend fun loginWithNaver(): PostLoginRequest = loginRepository.loginWithNaver()

        suspend fun loginWithGoogle(activity: Activity): PostLoginRequest? = loginRepository.loginWithGoogle(activity)
    }
