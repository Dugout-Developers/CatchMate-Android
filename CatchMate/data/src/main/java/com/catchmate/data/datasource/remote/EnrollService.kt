package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.EnrollCancelResponseDTO
import com.catchmate.data.dto.EnrollRequestDTO
import com.catchmate.data.dto.EnrollResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EnrollService {
    @POST("enroll/{boardId}")
    suspend fun postEnroll(
        @Path("boardId") boardId: Long,
        @Body enrollRequestDTO: EnrollRequestDTO,
    ): Response<EnrollResponseDTO?>

    @POST("enroll/cancel/{enrollId}")
    suspend fun postEnrollCancel(
        @Path("enrollId") enrollId: Long,
    ): Response<EnrollCancelResponseDTO?>
}
