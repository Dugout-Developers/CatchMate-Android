package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.DeleteBoardRequestDTO
import com.catchmate.data.dto.GetBoardPagingResponseDTO
import com.catchmate.data.dto.GetBoardResponseDTO
import com.catchmate.data.dto.GetLikedBoardResponseDTO
import com.catchmate.data.dto.PostBoardRequestDTO
import com.catchmate.data.dto.PostBoardResponseDTO
import com.catchmate.data.dto.PutBoardRequestDTO
import com.catchmate.data.dto.PutBoardResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @POST("board/like/{boardId}")
    suspend fun postBoardLike(
        @Path("boardId") boardId: Long,
        @Query("flag") flag: Int,
    ): Response<Int>

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

    @GET("board/{boardId}")
    suspend fun getBoard(
        @Path("boardId") boardId: Long,
    ): Response<GetBoardResponseDTO?>

    @GET("board/likes")
    suspend fun getLikedBoard(): Response<List<GetLikedBoardResponseDTO>?>

    @DELETE("board/remove")
    suspend fun deleteBoard(
        @Body deleteBoardRequest: DeleteBoardRequestDTO,
    ): Response<Int>
}
