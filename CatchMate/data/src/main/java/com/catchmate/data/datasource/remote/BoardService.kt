package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.PostBoardRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BoardService {
    @POST("board/write")
    suspend fun postBoard(
        @Body postBoardRequestDTO: PostBoardRequestDTO,
    ): Response<PostBoardResponseDTO?>
}
