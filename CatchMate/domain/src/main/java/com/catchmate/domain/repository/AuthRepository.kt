package com.catchmate.domain.repository

import com.catchmate.domain.model.PostLoginRequest
import com.catchmate.domain.model.PostLoginResponse

interface AuthRepository {
    suspend fun postAuthLogin(postLoginRequest: PostLoginRequest): PostLoginResponse?
}
