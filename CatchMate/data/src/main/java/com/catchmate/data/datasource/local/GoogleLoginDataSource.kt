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
        suspend fun getCredential(activity: Activity): GetCredentialResponse? {
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
                credentialManager.getCredential(activity, request)
            } catch (e: NoCredentialException) {
                Log.e("GoogleLoginError", "No credentials found: ${e.message}")
                null
            }
        }

        fun handleSignIn(result: GetCredentialResponse): PostLoginRequestDTO? {
            val credential = result.credential

            return if (credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        val email = googleIdTokenCredential.id
                        val profileUri = googleIdTokenCredential.profilePictureUri

                        Log.i("GoogleInfoSuccess", "idToken : $idToken  email : $email profileUri : $profileUri")
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
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("GoogleInfoError", "Received an invalid google id token response", e)
                        null
                    }
                } else {
                    Log.e("GoogleInfoError", "Unexpected type of credential")
                    null
                }
            } else {
                Log.e("GoogleInfoError", "Unexpected type of credential")
                null
            }
        }
    }
