package com.catchmate.data.repository

import com.catchmate.data.datasource.local.KakaoLoginDataSource
import com.catchmate.data.datasource.local.NaverLoginDataSource
import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl
    @Inject
    constructor(
        private val kakaoLoginDataSource: KakaoLoginDataSource,
        private val naverLoginDataSource: NaverLoginDataSource,
    ) : LoginRepository {
        override fun loginWithKakao() {
            kakaoLoginDataSource.loginWithKakao()
        }

        override fun loginWithNaver() {
            naverLoginDataSource.loginWithNaver()
        }
    }
