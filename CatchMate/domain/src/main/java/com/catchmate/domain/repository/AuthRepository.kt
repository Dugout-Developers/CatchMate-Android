package com.catchmate.domain.repository

import com.catchmate.domain.model.DeleteLogoutResponse
import com.catchmate.domain.model.GetCheckNicknameResponse
import com.catchmate.domain.model.PostLoginRequest
import com.catchmate.domain.model.PostLoginResponse

interface AuthRepository {
    suspend fun postAuthLogin(postLoginRequest: PostLoginRequest): PostLoginResponse?

    suspend fun getAuthCheckNickname(nickName: String): Result<GetCheckNicknameResponse>

    suspend fun deleteAuthLogout(refreshToken: String): Result<DeleteLogoutResponse>
}
