package com.catchmate.domain.usecase

import com.catchmate.domain.repository.BoardLikeRepository
import javax.inject.Inject

class BoardLikeUseCase
    @Inject
    constructor(
        private val boardLikeRepository: BoardLikeRepository,
    ) {
        suspend fun postBoardLike(
            accessToken: String,
            boardId: Long,
            flag: Int,
        ): Int? = boardLikeRepository.postBoardLike(accessToken, boardId, flag)
    }
