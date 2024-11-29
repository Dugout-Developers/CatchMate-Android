package com.catchmate.data.repository

import com.catchmate.data.datasource.local.GoogleLoginDataSource
import com.catchmate.data.datasource.local.KakaoLoginDataSource
import com.catchmate.data.datasource.local.NaverLoginDataSource
import com.catchmate.data.mapper.AuthMapper
import com.catchmate.domain.model.PostLoginRequest
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

        override suspend fun loginWithGoogle(): PostLoginRequest? {
            val result = googleLoginDataSource.getCredential()
            val postLoginRequestDTO = googleLoginDataSource.handleSignIn(result)
            return if (postLoginRequestDTO == null) {
                null
            } else {
                AuthMapper.toPostLoginRequest(postLoginRequestDTO)
            }
        }
    }
