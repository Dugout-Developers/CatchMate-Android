package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardReadResponse

interface BoardReadRepository {
    suspend fun getBoard(
        accessToken: String,
        boardId: Long,
    ): BoardReadResponse?
}
