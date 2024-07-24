package com.catchmate.data.datasource.local

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class KakaoLoginDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val userApiClient: UserApiClient,
    ) {
        private val isKakaoTalkLoginAvailable: Boolean
            get() = userApiClient.isKakaoTalkLoginAvailable(context)

        fun loginWithKakao() {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    loginFail(error)
                } else if (token != null) {
                    loginSuccess(token)
                }
            }

            if (isKakaoTalkLoginAvailable) {
                userApiClient.loginWithKakaoTalk(context, callback = callback)
            } else {
                userApiClient.loginWithKakaoAccount(context, callback = callback)
            }
        }

        private fun loginFail(throwable: Throwable) {
            val loginType = if (isKakaoTalkLoginAvailable) KAKAO_TALK else KAKAO_ACCOUNT
            Log.e("KakaoLoginFail", "${loginType}으로 로그인 실패")
            throwable.printStackTrace()
        }

        private fun loginSuccess(oAuthToken: OAuthToken) {
            val loginType = if (isKakaoTalkLoginAvailable) KAKAO_TALK else KAKAO_ACCOUNT
            Log.i("KakaoLoginSuccess", "${loginType}으로 로그인 성공 ${oAuthToken.accessToken}")

            userApiClient.me { user, error ->
                if (error != null) {
                    Log.e("KakaoInfoFail", "사용자 정보 요청 실패")
                    error.printStackTrace()
                } else if (user != null) {
                    Log.d("KakaoInfoSuccess", "providerId : ${user.id}")
                }
            }
        }

        companion object {
            const val KAKAO_TALK = "카카오톡"
            const val KAKAO_ACCOUNT = "카카오계정"
        }
    }
