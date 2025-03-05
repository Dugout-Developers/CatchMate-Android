package com.catchmate.data.repository

import android.app.Activity
import com.catchmate.data.datasource.local.GoogleLoginDataSource
import com.catchmate.data.datasource.local.KakaoLoginDataSource
import com.catchmate.data.datasource.local.NaverLoginDataSource
import com.catchmate.data.mapper.AuthMapper
import com.catchmate.domain.model.auth.PostLoginRequest
import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl
    @Inject
    constructor(
        private val kakaoLoginDataSource: KakaoLoginDataSource,
        private val naverLoginDataSource: NaverLoginDataSource,
        private val googleLoginDataSource: GoogleLoginDataSource,
    ) : LoginRepository {
        override suspend fun loginWithKakao(): PostLoginRequest {
            val postLoginRequestDTO = kakaoLoginDataSource.loginWithKakao()
            return AuthMapper.toPostLoginRequest(postLoginRequestDTO)
        }

        override suspend fun loginWithNaver(): PostLoginRequest {
            val postLoginRequestDTO = naverLoginDataSource.loginWithNaver()
            return AuthMapper.toPostLoginRequest(postLoginRequestDTO)
        }

        override suspend fun loginWithGoogle(activity: Activity): PostLoginRequest? {
            val result = googleLoginDataSource.getCredential(activity)
            val postLoginRequestDTO = result?.let { googleLoginDataSource.handleSignIn(it) }
            return if (postLoginRequestDTO == null) {
                null
            } else {
                AuthMapper.toPostLoginRequest(postLoginRequestDTO)
            }
        }
    }
