package com.catchmate.data.repository

import com.catchmate.data.datasource.local.GoogleLoginDataSource
import com.catchmate.data.datasource.local.KakaoLoginDataSource
import com.catchmate.data.datasource.local.NaverLoginDataSource
import com.catchmate.data.mapper.AuthMapper
import com.catchmate.domain.model.LoginRequest
import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl
    @Inject
    constructor(
        private val kakaoLoginDataSource: KakaoLoginDataSource,
        private val naverLoginDataSource: NaverLoginDataSource,
        private val googleLoginDataSource: GoogleLoginDataSource,
    ) : LoginRepository {
        override suspend fun loginWithKakao(): LoginRequest {
            val loginRequestDTO = kakaoLoginDataSource.loginWithKakao()
            return AuthMapper.toLoginRequest(loginRequestDTO)
        }

        override suspend fun loginWithNaver(): LoginRequest {
            val loginRequestDTO = naverLoginDataSource.loginWithNaver()
            return AuthMapper.toLoginRequest(loginRequestDTO)
        }

        override suspend fun loginWithGoogle(): LoginRequest? {
            val result = googleLoginDataSource.getCredential()
            val loginRequestDTO = googleLoginDataSource.handleSignIn(result)
            return if (loginRequestDTO == null) {
                loginRequestDTO
            } else {
                AuthMapper.toLoginRequest(loginRequestDTO)
            }
        }
    }
