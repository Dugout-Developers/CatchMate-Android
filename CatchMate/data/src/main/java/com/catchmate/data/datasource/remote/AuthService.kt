package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.auth.DeleteLogoutResponseDTO
import com.catchmate.data.dto.auth.PostLoginRequestDTO
import com.catchmate.data.dto.auth.PostLoginResponseDTO
import com.catchmate.data.dto.auth.PostReissueResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/login")
    suspend fun postAuthLogin(
        @Body loginRequestDTO: PostLoginRequestDTO,
    ): Response<PostLoginResponseDTO?>

    @POST("api/auth/reissue")
    suspend fun postAuthReissue(
        @Header("RefreshToken") refreshToken: String,
    ): Response<PostReissueResponseDTO?>

    @POST("api/auth/logout")
    suspend fun deleteAuthLogout(
        @Header("RefreshToken") refreshToken: String,
    ): Response<Int>
}
