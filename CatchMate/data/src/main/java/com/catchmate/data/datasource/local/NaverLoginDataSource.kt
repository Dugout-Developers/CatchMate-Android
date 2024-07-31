package com.catchmate.data.datasource.local

import android.content.Context
import android.util.Log
import com.catchmate.data.datasource.remote.FCMTokenService
import com.catchmate.data.dto.LoginRequestDTO
import com.catchmate.domain.model.LoginPlatform
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NaverLoginDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val fcmTokenService: FCMTokenService,
    ) {
        suspend fun loginWithNaver(): LoginRequestDTO =
            suspendCancellableCoroutine { continuation ->
                val nidProfileCallback =
                    object : NidProfileCallback<NidProfileResponse> {
                        override fun onError(
                            errorCode: Int,
                            message: String,
                        ) {
                            onFailure(errorCode, message)
                            continuation.resumeWithException(Exception(message))
                        }

                        override fun onFailure(
                            httpStatus: Int,
                            message: String,
                        ) {
                            loginFail()
                            continuation.resumeWithException(Exception(message))
                        }

                        override fun onSuccess(result: NidProfileResponse) {
                            if (result.profile != null) {
                                Log.d("NaverInfoSuccess", "providerId : ${result.profile?.id} email : ${result.profile?.email}")
                                result.profile?.let {
                                    val loginRequestDTO =
                                        LoginRequestDTO(
                                            email = it.email!!,
                                            providerId = it.id!!,
                                            provider = LoginPlatform.NAVER.toString().lowercase(),
                                            picture = it.profileImage!!,
                                            fcmToken =
                                                runBlocking {
                                                    fcmTokenService.getToken()
                                                },
                                        )
                                    continuation.resume(loginRequestDTO)
                                } ?: continuation.resumeWithException(Exception("Profile is null"))
                            }
                        }
                    }

                val oAuthLoginCallback =
                    object : OAuthLoginCallback {
                        override fun onError(
                            errorCode: Int,
                            message: String,
                        ) {
                            onFailure(errorCode, message)
                        }

                        override fun onFailure(
                            httpStatus: Int,
                            message: String,
                        ) {
                            loginFail()
                        }

                        override fun onSuccess() {
                            Log.d("NaverLoginSuccess", "네이버 로그인 성공")
                            NidOAuthLogin().callProfileApi(nidProfileCallback)
                        }
                    }

                NaverIdLoginSDK.authenticate(context, oAuthLoginCallback)
            }

        private fun loginFail() {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            Log.e("NaverLoginFail", "errorCode:$errorCode errorDescription:$errorDescription")
        }
    }
