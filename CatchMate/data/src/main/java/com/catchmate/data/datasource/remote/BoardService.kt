package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.GetBoardPagingResponseDTO
import com.catchmate.data.dto.PutBoardRequestDTO
import com.catchmate.data.dto.PostBoardRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import com.catchmate.data.dto.PutBoardResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
    @POST("board/write")
    suspend fun postBoard(
        @Body postBoardRequestDTO: PostBoardRequestDTO,
    ): Response<PostBoardResponseDTO?>

    @PUT("board/edit")
    suspend fun putBoard(
        @Body putBoardRequestDTO: PutBoardRequestDTO,
    ): Response<PutBoardResponseDTO?>

    @GET("board/page/{pageNum}")
    suspend fun getBoardPaging(
        @Path("pageNum") pageNum: Long,
        @Query("gudans") gudans: String,
        @Query("people") people: Int,
        @Query("gameDate") gameDate: String,
    ): Response<List<GetBoardPagingResponseDTO>?>
}
