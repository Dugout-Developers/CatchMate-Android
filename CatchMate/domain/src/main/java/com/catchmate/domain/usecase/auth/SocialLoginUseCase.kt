package com.catchmate.domain.usecase.auth

import android.app.Activity
import com.catchmate.domain.exception.Result
import com.catchmate.domain.model.auth.UserData
import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class SocialLoginUseCase
    @Inject
    constructor(
        private val loginRepository: LoginRepository,
    ) {
        suspend fun loginWithKakao(): UserData? = loginRepository.loginWithKakao()

        suspend fun loginWithNaver(activity: Activity): UserData? = loginRepository.loginWithNaver(activity)

        suspend fun loginWithGoogle(activity: Activity): Result<UserData> = loginRepository.loginWithGoogle(activity)
    }
