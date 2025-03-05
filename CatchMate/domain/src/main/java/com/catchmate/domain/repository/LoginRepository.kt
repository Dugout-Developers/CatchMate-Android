package com.catchmate.domain.repository

import android.app.Activity
import com.catchmate.domain.model.auth.PostLoginRequest

interface LoginRepository {
    suspend fun loginWithKakao(): PostLoginRequest

    suspend fun loginWithNaver(): PostLoginRequest

    suspend fun loginWithGoogle(activity: Activity): PostLoginRequest?
}
