package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.BoardWriteRequestDTO
import com.catchmate.data.dto.BoardWriteResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BoardWriteService {
    @POST("board/write")
    suspend fun postBoardWrite(
        @Body boardWriteRequestDTO: BoardWriteRequestDTO,
    ): Response<BoardWriteResponseDTO?>
}
