package com.catchmate.data.datasource.local

import android.content.Context
import android.util.Log
import com.catchmate.data.datasource.remote.FCMTokenService
import com.catchmate.data.dto.LoginRequestDTO
import com.catchmate.domain.model.LoginPlatform
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoLoginDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val userApiClient: UserApiClient,
        private val fcmTokenService: FCMTokenService,
    ) {
        private val isKakaoTalkLoginAvailable: Boolean
            get() = userApiClient.isKakaoTalkLoginAvailable(context)

        suspend fun loginWithKakao(): LoginRequestDTO = suspendCancellableCoroutine { continuation ->
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    loginFail(error)
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    val loginType = if (isKakaoTalkLoginAvailable) KAKAO_TALK else KAKAO_ACCOUNT
                    Log.i("KakaoLoginSuccess", "${loginType}으로 로그인 성공 ${token.accessToken}")

                    userApiClient.me { user, error ->
                        if (error != null) {
                            Log.e("KakaoInfoFail", "사용자 정보 요청 실패")
                            error.printStackTrace()
                            continuation.resumeWithException(error)
                        } else if (user != null) {
                            Log.d("KakaoInfoSuccess", "providerId : ${user.id}")
                            user.let {
                                val loginRequestDTO = LoginRequestDTO(
                                    email = it.kakaoAccount?.email!!,
                                    providerId = it.id.toString(),
                                    provider = LoginPlatform.KAKAO.toString().lowercase(),
                                    picture = user.kakaoAccount?.profile?.profileImageUrl!!,
                                    fcmToken =
                                    runBlocking {
                                        fcmTokenService.getToken()
                                    },
                                )
                                continuation.resume(loginRequestDTO)
                            }
                        } else {
                            continuation.resumeWithException(Exception("Profile is null"))
                        }
                    }
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

        companion object {
            const val KAKAO_TALK = "카카오톡"
            const val KAKAO_ACCOUNT = "카카오계정"
        }
    }
