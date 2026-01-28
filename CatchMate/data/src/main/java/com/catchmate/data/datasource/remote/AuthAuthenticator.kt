package com.catchmate.data.datasource.remote

import com.catchmate.data.datasource.local.LocalStorageDataSource
import com.catchmate.domain.exception.ReissueFailureException
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator
    @Inject
    constructor(
        private val localStorageDataSource: LocalStorageDataSource,
        private val authRetrofitClient: AuthRetrofitClient,
    ) : Authenticator {
        override fun authenticate(
            route: Route?,
            response: Response,
        ): Request? {
            // 무한 루프 방지 - 이미 인증을 시도한 응답(priorResponse)이 있다면 중단
            if (response.priorResponse != null) {
                return null
            }

            // 동기화 - 여러 스레드에서 동시에 토큰 갱신을 시도하는 것을 방지
            synchronized(this) {
                val currentAccessToken =
                    runBlocking {
                        localStorageDataSource.getAccessToken()
                    }

                // 현재 요청 헤더에 있는 토큰과 저장된 최신 토큰을 비교
                // 다르다면 다른 스레드에서 이미 토큰을 갱신했다는 뜻이므로
                // 갱신 API 호출 없이 바로 새 토큰으로 재요청만 생성
                val requestAccessToken = response.request.header("Authorization")
                if (requestAccessToken != null && requestAccessToken != currentAccessToken) {
                    return sendNewRequest(response.request, currentAccessToken)
                }

                val refreshToken =
                    runBlocking {
                        localStorageDataSource.getRefreshToken()
                    }

                val reissueResponse =
                    runBlocking {
                        authRetrofitClient.retrofit.postAuthReissue(refreshToken)
                    }

                return if (reissueResponse.isSuccessful) {
                    val newAccessToken = reissueResponse.body()?.accessToken

                    if (newAccessToken != null) {
                        localStorageDataSource.saveAccessToken(newAccessToken)
                        sendNewRequest(response.request, newAccessToken)
                    } else {
                        throw ReissueFailureException("Reissue Error - AccessToken is null")
                    }
                } else {
                    throw ReissueFailureException("Reissue Error - Reissue Failure")
                }
            }

        }

        private fun sendNewRequest(request: Request, accessToken: String): Request =
            request.newBuilder()
                .header("Authorization", accessToken)
                .build()
    }
