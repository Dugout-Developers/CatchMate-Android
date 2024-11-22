package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.PostLoginRequestDTO
import com.catchmate.data.dto.PostLoginResponseDTO
import com.catchmate.data.dto.ReissueResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun postAuthLogin(
        @Body loginRequestDTO: PostLoginRequestDTO,
    ): Response<PostLoginResponseDTO?>

    @POST("auth/reissue")
    suspend fun postReissue(
        @Header("RefreshToken") refreshToken: String,
    ): Response<ReissueResponseDTO?>
}
