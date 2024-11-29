package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.EnrollCancelResponseDTO
import com.catchmate.data.dto.PostEnrollRequestDTO
import com.catchmate.data.dto.PostEnrollResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EnrollService {
    @POST("enroll/{boardId}")
    suspend fun postEnroll(
        @Path("boardId") boardId: Long,
        @Body postEnrollRequestDTO: PostEnrollRequestDTO,
    ): Response<PostEnrollResponseDTO?>

    @POST("enroll/cancel/{enrollId}")
    suspend fun postEnrollCancel(
        @Path("enrollId") enrollId: Long,
    ): Response<EnrollCancelResponseDTO?>
}
