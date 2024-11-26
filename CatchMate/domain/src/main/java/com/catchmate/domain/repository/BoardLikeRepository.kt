package com.catchmate.domain.repository

import com.catchmate.domain.model.GetBoardPagingResponse

interface BoardLikeRepository {
    suspend fun getBoardLikedList(): Result<List<GetBoardPagingResponse>>
}
