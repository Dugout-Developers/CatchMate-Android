package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.AuthService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.AuthMapper
import com.catchmate.domain.model.LoginRequest
import com.catchmate.domain.model.LoginResponse
import com.catchmate.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : AuthRepository {
        private val authApi = retrofitClient.createApi<AuthService>()

        override suspend fun postLogin(loginRequest: LoginRequest): LoginResponse? =
            try {
                val response = authApi.postLogin(AuthMapper.toLoginRequestDTO(loginRequest))
                if (response.isSuccessful) {
                    Log.d("AuthRepository", "통신 성공 : ${response.code()}")
                    response.body()?.let { AuthMapper.toLoginResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    Log.d("AuthRepository", "통신 실패 : ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
