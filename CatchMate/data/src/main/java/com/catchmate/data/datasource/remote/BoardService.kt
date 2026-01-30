package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.board.DeleteBoardLikeResponseDTO
import com.catchmate.data.dto.board.DeleteBoardResponseDTO
import com.catchmate.data.dto.board.GetBoardListResponseDTO
import com.catchmate.data.dto.board.GetBoardResponseDTO
import com.catchmate.data.dto.board.GetLikedBoardResponseDTO
import com.catchmate.data.dto.board.GetTempBoardResponseDTO
import com.catchmate.data.dto.board.GetUserBoardListResponseDTO
import com.catchmate.data.dto.board.PatchBoardLiftUpResponseDTO
import com.catchmate.data.dto.board.PutBoardRequestDTO
import com.catchmate.data.dto.board.PutBoardResponseDTO
import com.catchmate.data.dto.board.PostBoardRequestDTO
import com.catchmate.data.dto.board.PostBoardResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
    @POST("api/boards")
    suspend fun postBoard(
        @Body postBoardRequestDTO: PostBoardRequestDTO,
    ): Response<PostBoardResponseDTO?>

    @POST("api/bookmarks/{boardId}")
    suspend fun postBoardLike(
        @Path("boardId") boardId: Long,
    ): Response<Unit>

    @PUT("api/boards/{boardId}")
    suspend fun putBoard(
        @Path("boardId") boardId: Long,
        @Body putBoardRequestDTO: PutBoardRequestDTO,
    ): Response<PutBoardResponseDTO?>

    @PATCH("boards/{boardId}/lift-up")
    suspend fun patchBoardLiftUp(
        @Path("boardId") boardId: Long,
    ): Response<PatchBoardLiftUpResponseDTO?>

    // 필터 미지정 시 모든 쿼리 값 안넣고 호출
    @GET("api/boards")
    suspend fun getBoardList(
        @Query("gameDate") gameDate: String? = null,
        @Query("maxPerson") maxPerson: Int? = null,
        @Query("preferredTeamIdList") preferredTeamIdList: Array<Int>? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<GetBoardListResponseDTO?>

    @GET("boards/list/{userId}")
    suspend fun getUserBoardList(
        @Path("userId") userId: Long,
        @Query("page") page: Int,
    ): Response<GetUserBoardListResponseDTO?>

    @GET("api/boards/{boardId}")
    suspend fun getBoard(
        @Path("boardId") boardId: Long,
    ): Response<GetBoardResponseDTO?>

    @GET("boards/bookmark")
    suspend fun getLikedBoard(
        @Query("page") page: Int,
    ): Response<GetLikedBoardResponseDTO?>

    @GET("api/boards/temp")
    suspend fun getTempBoard(): Response<GetTempBoardResponseDTO?>

    @DELETE("api/boards/{boardId}")
    suspend fun deleteBoard(
        @Path("boardId") boardId: Long,
    ): Response<Unit>

    // 안쓰는 api 제거
    @DELETE("boards/bookmark/{boardId}")
    suspend fun deleteBoardLike(
        @Path("boardId") boardId: Long,
    ): Response<DeleteBoardLikeResponseDTO?>
}
