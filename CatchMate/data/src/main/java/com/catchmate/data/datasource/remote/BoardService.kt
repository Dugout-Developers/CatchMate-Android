package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.board.DeleteBoardRequestDTO
import com.catchmate.data.dto.board.GetBoardPagingResponseDTO
import com.catchmate.data.dto.board.GetBoardResponseDTO
import com.catchmate.data.dto.board.GetLikedBoardResponseDTO
import com.catchmate.data.dto.board.PostBoardRequestDTO
import com.catchmate.data.dto.board.PostBoardResponseDTO
import com.catchmate.data.dto.board.PutBoardRequestDTO
import com.catchmate.data.dto.board.PutBoardResponseDTO
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
