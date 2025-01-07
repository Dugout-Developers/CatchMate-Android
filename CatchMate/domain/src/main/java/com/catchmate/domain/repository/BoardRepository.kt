package com.catchmate.domain.repository

import com.catchmate.domain.model.board.DeleteBoardRequest
import com.catchmate.domain.model.board.GetBoardListResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
import com.catchmate.domain.model.board.PutBoardRequest
import com.catchmate.domain.model.board.PutBoardResponse

interface BoardRepository {
    suspend fun postBoard(postBoardRequest: PostBoardRequest): Result<PostBoardResponse>

    suspend fun postBoardLike(
        boardId: Long,
        flag: Int,
    ): Result<Int>

    suspend fun putBoard(putBoardRequest: PutBoardRequest): Result<PutBoardResponse>

    suspend fun getBoardList(
        gameStartDate: String?,
        maxPerson: Int?,
        preferredTeamId: Int?,
    ): Result<GetBoardListResponse>

    suspend fun getBoard(boardId: Long): Result<GetBoardResponse>

    suspend fun getLikedBoard(): Result<List<GetLikedBoardResponse>>

    suspend fun deleteBoard(boardDeleteRequest: DeleteBoardRequest): Result<Int>
}
