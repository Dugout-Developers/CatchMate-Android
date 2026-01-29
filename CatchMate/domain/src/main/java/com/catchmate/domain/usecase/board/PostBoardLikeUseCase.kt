package com.catchmate.domain.usecase.board

import com.catchmate.domain.repository.BoardRepository
import javax.inject.Inject

class PostBoardLikeUseCase
    @Inject
    constructor(
        private val boardRepository: BoardRepository,
    ) {
        suspend fun postBoardLike(boardId: Long): Result<Unit> = boardRepository.postBoardLike(boardId)
    }
