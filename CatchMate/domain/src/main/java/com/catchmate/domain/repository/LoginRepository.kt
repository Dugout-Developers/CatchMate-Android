package com.catchmate.domain.repository

import com.catchmate.domain.model.LoginRequest

interface LoginRepository {
    suspend fun loginWithKakao(): LoginRequest

    suspend fun loginWithNaver(): LoginRequest

    suspend fun loginWithGoogle(): LoginRequest?
}
