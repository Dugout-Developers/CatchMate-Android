package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.BoardReadResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BoardReadService {
    @GET("board/{boardId}")
    suspend fun getBoard(
        @Path("boardId") boardId: Long,
    ): Response<BoardReadResponseDTO?>
}
