package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardListResponse

interface BoardListRepository {
    suspend fun getBoardList(
        accessToken: String,
        pageNum: Long,
        gudans: String,
        people: Int,
        gameDate: String,
    ): List<BoardListResponse>?
}
