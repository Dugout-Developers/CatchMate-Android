package com.catchmate.domain.repository

import android.app.Activity
import com.catchmate.domain.exception.Result
import com.catchmate.domain.model.auth.UserData

interface LoginRepository {
    suspend fun loginWithKakao(): UserData?

    suspend fun loginWithNaver(activity: Activity): UserData?

    suspend fun loginWithGoogle(activity: Activity): Result<UserData>
}
