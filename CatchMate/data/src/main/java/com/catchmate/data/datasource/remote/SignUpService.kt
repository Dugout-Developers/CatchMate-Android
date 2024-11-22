package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.UserAdditionalInfoRequestDTO
import com.catchmate.data.dto.UserAdditionalInfoResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("user/additional-info")
    suspend fun postUserAdditionalInfo(
        @Body userAdditionalInfoRequestDTO: UserAdditionalInfoRequestDTO,
    ): Response<UserAdditionalInfoResponseDTO?>
}
