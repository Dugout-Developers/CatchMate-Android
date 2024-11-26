package com.catchmate.domain.repository

import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse
import com.catchmate.domain.model.PutBoardRequest
import com.catchmate.domain.model.PutBoardResponse

interface BoardRepository {
    suspend fun postBoard(postBoardRequest: PostBoardRequest): Result<PostBoardResponse>

    suspend fun putBoard(putBoardRequest: PutBoardRequest): Result<PutBoardResponse>
}
