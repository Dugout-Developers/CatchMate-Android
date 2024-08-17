package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.CheckNicknameResponseDTO
import com.catchmate.data.dto.UserAdditionalInfoRequestDTO
import com.catchmate.data.dto.UserResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query

interface SignUpService {
    @GET("auth/check-nickname")
    suspend fun getNicknameAvailability(
        @Header("AccessToken") accessToken: String,
        @Query("nickName") nickName: String,
    ): Response<CheckNicknameResponseDTO?>

    @PATCH("user/additional-info")
    suspend fun patchUserAdditionalInfo(
        @Header("AccessToken") accessToken: String,
        @Body userAdditionalInfoRequestDTO: UserAdditionalInfoRequestDTO,
    ): Response<UserResponseDTO?>
}
