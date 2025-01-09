package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.board.DeleteBoardLikeResponseDTO
import com.catchmate.data.dto.board.DeleteBoardResponseDTO
import com.catchmate.data.dto.board.GetBoardListResponseDTO
import com.catchmate.data.dto.board.GetBoardResponseDTO
import com.catchmate.data.dto.board.GetLikedBoardResponseDTO
import com.catchmate.data.dto.board.PatchBoardLiftUpResponseDTO
import com.catchmate.data.dto.board.PatchBoardRequestDTO
import com.catchmate.data.dto.board.PatchBoardResponseDTO
import com.catchmate.data.dto.board.PostBoardLikeResponseDTO
import com.catchmate.data.dto.board.PostBoardRequestDTO
import com.catchmate.data.dto.board.PostBoardResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
    @POST("board")
    suspend fun postBoard(
        @Body postBoardRequestDTO: PostBoardRequestDTO,
    ): Response<PostBoardResponseDTO?>

    @POST("board/bookmark/{boardId}")
    suspend fun postBoardLike(
        @Path("boardId") boardId: Long,
    ): Response<PostBoardLikeResponseDTO?>

    @PATCH("board/{boardId}")
    suspend fun patchBoard(
        @Path("boardId") boardId: Long,
        @Body patchBoardRequestDTO: PatchBoardRequestDTO,
    ): Response<PatchBoardResponseDTO?>

    @PATCH("board/{boardId}/lift-up")
    suspend fun patchBoardLiftUp(
        @Path("boardId") boardId: Long,
    ): Response<PatchBoardLiftUpResponseDTO?>

    // 필터 미지정 시 모든 쿼리 값 안넣고 호출
    @GET("board/list")
    suspend fun getBoardList(
        @Query("gameStartDate") gameStartDate: String? = null,
        @Query("maxPerson") maxPerson: Int? = null,
        @Query("preferredTeamId") preferredTeamId: Int?= null,
    ): Response<GetBoardListResponseDTO?>

    @GET("board/{boardId}")
    suspend fun getBoard(
        @Path("boardId") boardId: Long,
    ): Response<GetBoardResponseDTO?>

    @GET("board/bookmark")
    suspend fun getLikedBoard(): Response<GetLikedBoardResponseDTO?>

    @DELETE("board/{boardId}")
    suspend fun deleteBoard(
        @Path("boardId") boardId: Long,
    ): Response<DeleteBoardResponseDTO?>

    @DELETE("board/bookmark/{boardId}")
    suspend fun deleteBoardLike(
        @Path("boardId") boardId: Long,
    ): Response<DeleteBoardLikeResponseDTO?>
}
