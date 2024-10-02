package com.catchmate.data.datasource.remote

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardLikeService {
    @POST("board/like/{boardId}")
    suspend fun postBoardLike(
        @Path("boardId") boardId: Long,
        @Query("flag") flag: Int,
    ): Response<Int>
}
