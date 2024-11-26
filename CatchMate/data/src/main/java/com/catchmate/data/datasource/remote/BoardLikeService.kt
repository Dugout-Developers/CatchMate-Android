package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.GetBoardPagingResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface BoardLikeService {
    @GET("board/likes")
    suspend fun getBoardLikedList(): Response<List<GetBoardPagingResponseDTO>>
}
