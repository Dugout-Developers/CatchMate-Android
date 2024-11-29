package com.catchmate.data.datasource.local

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.catchmate.data.BuildConfig
import com.catchmate.data.datasource.remote.FCMTokenService
import com.catchmate.data.dto.PostLoginRequestDTO
import com.catchmate.domain.model.LoginPlatform
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GoogleLoginDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val fcmTokenService: FCMTokenService,
    ) {
        suspend fun getCredential(): GetCredentialResponse {
            val credentialManager = CredentialManager.create(context)

            val googleIdOption: GetGoogleIdOption =
                GetGoogleIdOption
                    .Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setAutoSelectEnabled(true)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    .build()

            val request =
                GetCredentialRequest
                    .Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

            return credentialManager.getCredential(context, request)
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
