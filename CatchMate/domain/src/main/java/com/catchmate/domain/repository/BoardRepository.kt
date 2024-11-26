package com.catchmate.domain.repository

import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse

interface BoardRepository {
    suspend fun postBoard(postBoardRequest: PostBoardRequest): Result<PostBoardResponse>
}
