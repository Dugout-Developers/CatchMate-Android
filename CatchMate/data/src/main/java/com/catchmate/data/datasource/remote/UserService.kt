package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.GetUserProfileResponseDTO
import com.catchmate.data.dto.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.PostUserAdditionalInfoResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("user/profile")
    suspend fun getUserProfile(): Response<GetUserProfileResponseDTO?>

    @POST("user/additional-info")
    suspend fun postUserAdditionalInfo(
        @Body postUserAdditionalInfoRequestDTO: PostUserAdditionalInfoRequestDTO,
    ): Response<PostUserAdditionalInfoResponseDTO?>
}
