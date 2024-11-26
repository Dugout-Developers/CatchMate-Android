package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.BoardDeleteRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE

interface BoardReadService {
    @DELETE("board/remove")
    suspend fun deleteBoard(
        @Body boardDeleteRequest: BoardDeleteRequestDTO,
    ): Response<Int>
}
