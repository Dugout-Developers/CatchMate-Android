package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.AuthService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.AuthMapper
import com.catchmate.domain.model.auth.PostLoginRequest
import com.catchmate.domain.model.auth.PostLoginResponse
import com.catchmate.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : AuthRepository {
        private val authApi = retrofitClient.createApi<AuthService>()
        private val tag = "AuthRepo"

        override suspend fun postAuthLogin(postLoginRequest: PostLoginRequest): PostLoginResponse? =
            try {
                val response = authApi.postAuthLogin(AuthMapper.toPostLoginRequestDTO(postLoginRequest))
                if (response.isSuccessful) {
                    Log.d("AuthRepository", "통신 성공 : ${response.code()}")
                    response.body()?.let { AuthMapper.toPostLoginResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    Log.d("AuthRepository", "통신 실패 : ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        override suspend fun deleteAuthLogout(refreshToken: String): Result<Int> =
            try {
                val response = authApi.deleteAuthLogout(refreshToken)
                if (response.isSuccessful) {
                    Log.d("AuthRepository", "통신 성공 : ${response.code()}")
                    Result.success(response.code())
                } else {
                    Log.d("AuthRepository", "통신 실패 : ${response.code()}")
                    Result.failure(Exception(response.errorBody()?.toString()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
    }
