package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.BoardDeleteRequestDTO
import com.catchmate.data.dto.BoardReadResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface BoardReadService {
    @GET("board/{boardId}")
    suspend fun getBoard(
        @Path("boardId") boardId: Long,
    ): Response<BoardReadResponseDTO?>

    @DELETE("board/remove")
    suspend fun deleteBoard(
        @Body boardDeleteRequest: BoardDeleteRequestDTO,
    ): Response<Int>
}
