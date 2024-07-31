package com.catchmate.domain.repository

import com.catchmate.domain.model.LoginRequest
import com.catchmate.domain.model.LoginResponse

interface AuthRepository {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse?
}
