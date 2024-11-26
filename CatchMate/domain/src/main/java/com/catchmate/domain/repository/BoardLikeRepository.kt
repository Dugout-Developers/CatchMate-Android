package com.catchmate.domain.repository

import com.catchmate.domain.model.GetBoardPagingResponse

interface BoardLikeRepository {
    suspend fun postBoardLike(
        boardId: Long,
        flag: Int,
    ): Int?

    suspend fun getBoardLikedList(): Result<List<GetBoardPagingResponse>>
}
