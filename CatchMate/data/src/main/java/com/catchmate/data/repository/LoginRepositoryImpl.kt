package com.catchmate.data.repository

import com.catchmate.data.datasource.local.KakaoLoginDataSource
import com.catchmate.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl
    @Inject
    constructor(
        private val kakaoLoginDataSource: KakaoLoginDataSource,
    ) : LoginRepository {
        override fun loginWithKakao() {
            kakaoLoginDataSource.loginWithKakao()
        }
    }
