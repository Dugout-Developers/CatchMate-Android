package com.catchmate.data.datasource.local

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.NoCredentialException
import com.catchmate.data.BuildConfig
import com.catchmate.data.datasource.remote.FCMTokenService
import com.catchmate.data.dto.auth.PostLoginRequestDTO
import com.catchmate.domain.exception.GoogleLoginException
import com.catchmate.domain.model.enumclass.LoginPlatform
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GoogleLoginDataSource
    @Inject
    constructor(
        private val fcmTokenService: FCMTokenService,
    ) {
        suspend fun getCredential(activity: Activity): com.catchmate.domain.exception.Result<GetCredentialResponse> {
            val credentialManager = CredentialManager.create(activity)

            val googleIdOption: GetGoogleIdOption =
                GetGoogleIdOption
                    .Builder()
                    .setAutoSelectEnabled(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    .build()

            val request =
                GetCredentialRequest
                    .Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

            return try {
                val result = credentialManager.getCredential(activity, request)
                com.catchmate.domain.exception.Result.Success(result)
            } catch (e: Exception) {
                when (e) {
                    is GoogleLoginException.NoCredentials -> {
                        Log.e("GOOGLE - NOCredentials", "")
                        com.catchmate.domain.exception.Result.Error(exception = e)
                    }
                    is GoogleLoginException.Cancelled -> {
                        Log.e("GOOGLE - Cancelled", "")
                        com.catchmate.domain.exception.Result.Error(exception = e)
                    }
                    else -> {
                        Log.e("GOOGLE - ELSE", "")
                        com.catchmate.domain.exception.Result.Error(exception = e)
                    }
                }
            }
        }

        fun handleSignIn(result: GetCredentialResponse): com.catchmate.domain.exception.Result<PostLoginRequestDTO> {
            val credential = result.credential

            return if (credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        val email = googleIdTokenCredential.id
                        val profileUri = googleIdTokenCredential.profilePictureUri

                        Log.i("GoogleInfoSuccess", "idToken : $idToken  email : $email profileUri : $profileUri")
                        val loginRequestDTO =
                            PostLoginRequestDTO(
                                email = email,
                                providerId = idToken,
                                provider = LoginPlatform.GOOGLE.toString().lowercase(),
                                picture = profileUri.toString(),
                                fcmToken =
                                    runBlocking(Dispatchers.IO) {
                                        fcmTokenService.getToken()
                                    },
                            )
                        com.catchmate.domain.exception.Result.Success(loginRequestDTO)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("GoogleInfoError", "Received an invalid google id token response", e)
                        com.catchmate.domain.exception.Result.Error(exception = e)
                    } catch (e: Exception) {
                        Log.e("GoogleInfoError", "Unexpected error parsing credentials", e)
                        com.catchmate.domain.exception.Result.Error(exception = e)
                    }
                } else {
                    Log.e("GoogleInfoError", "Unexpected type of credential")
                    com.catchmate.domain.exception.Result.Error(exception = IllegalArgumentException("Unexpected credential type"))
                }
            } else {
                Log.e("GoogleInfoError", "Unexpected type of credential")
                com.catchmate.domain.exception.Result.Error(exception = IllegalArgumentException("Unexpected credential type"))
            }
        }
    }
