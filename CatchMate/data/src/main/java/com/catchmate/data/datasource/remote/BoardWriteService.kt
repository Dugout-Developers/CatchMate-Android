package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.BoardEditRequestDTO
import com.catchmate.data.dto.BoardWriteRequestDTO
import com.catchmate.data.dto.BoardWriteResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface BoardWriteService {
    @POST("board/write")
    suspend fun postBoardWrite(
        @Body boardWriteRequestDTO: BoardWriteRequestDTO,
    ): Response<BoardWriteResponseDTO?>

    @PUT("board/edit")
    suspend fun putBoard(
        @Body boardEditRequestDTO: BoardEditRequestDTO,
    ): Response<BoardWriteResponseDTO?>
}
