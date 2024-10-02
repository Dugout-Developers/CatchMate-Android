package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.GetUserProfileResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("user/profile")
    suspend fun getUserProfile(): Response<GetUserProfileResponseDTO?>
}
