package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardDeleteRequest

interface BoardReadRepository {
    suspend fun deleteBoard(boardDeleteRequest: BoardDeleteRequest): Int?
}
