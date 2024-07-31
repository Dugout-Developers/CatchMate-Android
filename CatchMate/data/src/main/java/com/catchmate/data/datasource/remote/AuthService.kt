package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.LoginRequestDTO
import com.catchmate.data.dto.LoginResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun postLogin(
        @Body loginRequestDTO: LoginRequestDTO,
    ): Response<LoginResponseDTO?>
}
