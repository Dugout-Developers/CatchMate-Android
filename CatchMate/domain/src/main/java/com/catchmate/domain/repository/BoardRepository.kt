package com.catchmate.domain.repository

import com.catchmate.domain.model.board.GetBoardListResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.model.board.GetTempBoardResponse
import com.catchmate.domain.model.board.GetUserBoardListResponse
import com.catchmate.domain.model.board.PatchBoardLiftUpResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
import com.catchmate.domain.model.board.PutBoardRequest
import com.catchmate.domain.model.board.PutBoardResponse

interface BoardRepository {
    suspend fun postBoard(postBoardRequest: PostBoardRequest): Result<PostBoardResponse>

    suspend fun postBoardLike(boardId: Long): Result<Unit>

    suspend fun putBoard(
        boardId: Long,
        putBoardRequest: PutBoardRequest,
    ): Result<PutBoardResponse>

    suspend fun patchBoardLiftUp(boardId: Long): Result<PatchBoardLiftUpResponse>

    suspend fun getBoardList(
        gameDate: String?,
        maxPerson: Int?,
        preferredTeamIdList: Array<Int>?,
        page: Int?,
        size: Int?,
    ): Result<GetBoardListResponse>

    suspend fun getUserBoardList(
        userId: Long,
        page: Int,
    ): Result<GetUserBoardListResponse>

    suspend fun getBoard(boardId: Long): Result<GetBoardResponse>

    suspend fun getLikedBoard(page: Int, size: Int): Result<GetLikedBoardResponse>

    suspend fun getTempBoard(): Result<GetTempBoardResponse?>

    suspend fun deleteBoard(boardId: Long): Result<Unit>
}
