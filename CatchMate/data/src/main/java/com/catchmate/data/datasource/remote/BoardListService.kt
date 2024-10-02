package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.BoardListResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardListService {
    @GET("board/page/{pageNum}")
    suspend fun getBoardList(
        @Path("pageNum") pageNum: Long,
        @Query("gudans") gudans: String,
        @Query("people") people: Int,
        @Query("gameDate") gameDate: String,
    ): Response<List<BoardListResponseDTO>?>
}
