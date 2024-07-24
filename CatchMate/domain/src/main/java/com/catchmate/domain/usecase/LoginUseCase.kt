package com.catchmate.domain.usecase

import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    fun loginWithKakao() {
        loginRepository.loginWithKakao()
    }
}