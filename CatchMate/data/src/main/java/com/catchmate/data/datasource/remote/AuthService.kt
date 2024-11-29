package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.GetCheckNicknameResponseDTO
import com.catchmate.data.dto.PostLoginRequestDTO
import com.catchmate.data.dto.PostLoginResponseDTO
import com.catchmate.data.dto.PostReissueResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("auth/login")
    suspend fun postAuthLogin(
        @Body loginRequestDTO: PostLoginRequestDTO,
    ): Response<PostLoginResponseDTO?>

    @POST("auth/reissue")
    suspend fun postAuthReissue(
        @Header("RefreshToken") refreshToken: String,
    ): Response<PostReissueResponseDTO?>

    @GET("auth/check-nickname")
    suspend fun getAuthCheckNickname(
        @Query("nickName") nickName: String,
    ): Response<GetCheckNicknameResponseDTO?>
}
