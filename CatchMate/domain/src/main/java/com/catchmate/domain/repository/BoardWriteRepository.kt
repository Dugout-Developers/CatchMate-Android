package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardEditRequest
import com.catchmate.domain.model.PostBoardResponse

interface BoardWriteRepository {
    suspend fun putBoard(boardEditRequest: BoardEditRequest): PostBoardResponse?
}
