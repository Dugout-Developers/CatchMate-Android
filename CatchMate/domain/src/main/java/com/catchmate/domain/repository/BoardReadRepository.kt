package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardReadResponse

interface BoardReadRepository {
    suspend fun getBoard(
        boardId: Long,
    ): BoardReadResponse?
}
