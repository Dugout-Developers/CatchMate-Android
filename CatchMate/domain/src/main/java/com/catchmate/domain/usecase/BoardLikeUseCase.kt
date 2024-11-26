package com.catchmate.domain.usecase

import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.repository.BoardLikeRepository
import javax.inject.Inject

class BoardLikeUseCase
    @Inject
    constructor(
        private val boardLikeRepository: BoardLikeRepository,
    ) {
        suspend fun getBoardLikedList(): Result<List<GetBoardPagingResponse>> = boardLikeRepository.getBoardLikedList()
    }
