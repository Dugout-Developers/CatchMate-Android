package com.catchmate.domain.repository

import com.catchmate.domain.model.PostLoginRequest

interface LoginRepository {
    suspend fun loginWithKakao(): PostLoginRequest

    suspend fun loginWithNaver(): PostLoginRequest

    suspend fun loginWithGoogle(): PostLoginRequest?
}
