package com.catchmate.data.datasource.local

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NaverLoginDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        fun loginWithNaver() {
            val nidProfileCallback =
                object : NidProfileCallback<NidProfileResponse> {
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

                    override fun onSuccess(result: NidProfileResponse) {
                        if (result.profile != null) {
                            Log.d("NaverInfoSuccess", "providerId : ${result.profile?.id} profile : ${result.profile?.profileImage}")
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
