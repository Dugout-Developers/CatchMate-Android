package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.board.GetBoardListResponseDTO
import com.catchmate.data.dto.board.GetBoardResponseDTO
import com.catchmate.data.dto.board.GetLikedBoardResponseDTO
import com.catchmate.data.dto.board.GetTempBoardResponseDTO
import com.catchmate.data.dto.board.GetUserBoardListResponseDTO
import com.catchmate.data.dto.board.PatchBoardLiftUpResponseDTO
import com.catchmate.data.dto.board.PostBoardLikeResponseDTO
import com.catchmate.data.dto.board.PostBoardRequestDTO
import com.catchmate.data.dto.board.PostBoardResponseDTO
import com.catchmate.data.dto.board.PutBoardRequestDTO
import com.catchmate.data.dto.board.PutBoardResponseDTO
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
    ): Response<PostBoardLikeResponseDTO>

    @PUT("api/boards/{boardId}")
    suspend fun putBoard(
        @Path("boardId") boardId: Long,
        @Body putBoardRequestDTO: PutBoardRequestDTO,
    ): Response<PutBoardResponseDTO?>

    @PATCH("api/boards/{boardId}/lift-up")
    suspend fun patchBoardLiftUp(
        @Path("boardId") boardId: Long,
    ): Response<PatchBoardLiftUpResponseDTO?>

    // 필터 미지정 시 모든 쿼리 값 안넣고 호출
    @GET("api/boards")
    suspend fun getBoardList(
        @Query("gameDate") gameDate: String? = null,
        @Query("maxPerson") maxPerson: Int? = null,
        @Query("preferredTeamIdList") preferredTeamIdList: Array<Int>? = null,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<GetBoardListResponseDTO?>

    @GET("api/boards/users/{userId}")
    suspend fun getUserBoardList(
        @Path("userId") userId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<GetUserBoardListResponseDTO?>

    @GET("api/boards/{boardId}")
    suspend fun getBoard(
        @Path("boardId") boardId: Long,
    ): Response<GetBoardResponseDTO?>

    @GET("api/bookmarks")
    suspend fun getLikedBoard(
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): Response<GetLikedBoardResponseDTO?>

    @GET("api/boards/temp")
    suspend fun getTempBoard(): Response<GetTempBoardResponseDTO?>

    @DELETE("api/boards/{boardId}")
    suspend fun deleteBoard(
        @Path("boardId") boardId: Long,
    ): Response<Unit>
}
