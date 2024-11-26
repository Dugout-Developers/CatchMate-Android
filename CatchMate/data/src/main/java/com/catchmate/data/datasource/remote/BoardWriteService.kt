package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.BoardEditRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface BoardWriteService {
    @PUT("board/edit")
    suspend fun putBoard(
        @Body boardEditRequestDTO: BoardEditRequestDTO,
    ): Response<PostBoardResponseDTO?>
}
