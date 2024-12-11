package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.PatchEnrollAcceptResponseDTO
import com.catchmate.data.dto.PatchEnrollRejectResponseDTO
import com.catchmate.data.dto.PostEnrollRequestDTO
import com.catchmate.data.dto.PostEnrollResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EnrollService {
    @POST("enroll/{boardId}")
    suspend fun postEnroll(
        @Path("boardId") boardId: Long,
        @Body postEnrollRequestDTO: PostEnrollRequestDTO,
    ): Response<PostEnrollResponseDTO?>

    @PATCH("enroll/{enrollId}/reject")
    suspend fun patchEnrollReject(
        @Path("enrollId") enrollId: Long,
    ): Response<PatchEnrollRejectResponseDTO?>

    @PATCH("enroll/{enrollId}/accept")
    suspend fun patchEnrollAccept(
        @Path("enrollId") enrollId: Long,
    ): Response<PatchEnrollAcceptResponseDTO?>
}
