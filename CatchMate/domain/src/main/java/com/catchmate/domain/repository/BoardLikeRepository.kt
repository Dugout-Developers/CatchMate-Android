package com.catchmate.domain.repository

interface BoardLikeRepository {
    suspend fun postBoardLike(
        accessToken: String,
        boardId: Long,
        flag: Int,
    ): Int?
}
