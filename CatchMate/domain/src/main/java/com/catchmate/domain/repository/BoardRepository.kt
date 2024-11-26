package com.catchmate.domain.repository

import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.model.GetBoardResponse
import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse
import com.catchmate.domain.model.PutBoardRequest
import com.catchmate.domain.model.PutBoardResponse

interface BoardRepository {
    suspend fun postBoard(postBoardRequest: PostBoardRequest): Result<PostBoardResponse>

    suspend fun putBoard(putBoardRequest: PutBoardRequest): Result<PutBoardResponse>

    suspend fun getBoardList(
        pageNum: Long,
        gudans: String,
        people: Int,
        gameDate: String,
    ): Result<List<GetBoardPagingResponse>>

    suspend fun getBoard(boardId: Long): Result<GetBoardResponse>
}
