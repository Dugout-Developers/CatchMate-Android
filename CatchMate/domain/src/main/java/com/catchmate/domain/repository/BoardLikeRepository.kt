package com.catchmate.domain.repository

interface BoardLikeRepository {
    suspend fun postBoardLike(
        boardId: Long,
        flag: Int,
    ): Int?
}
