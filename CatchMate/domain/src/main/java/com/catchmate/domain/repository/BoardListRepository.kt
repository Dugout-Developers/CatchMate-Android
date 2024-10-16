package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardListResponse

interface BoardListRepository {
    suspend fun getBoardList(
        pageNum: Long,
        gudans: String,
        people: Int,
        gameDate: String,
    ): Result<List<BoardListResponse>>
}
