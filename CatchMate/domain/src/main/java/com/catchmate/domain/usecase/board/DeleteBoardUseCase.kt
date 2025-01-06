package com.catchmate.domain.usecase.board

import com.catchmate.domain.model.board.DeleteBoardRequest
import com.catchmate.domain.repository.BoardRepository
import javax.inject.Inject

class DeleteBoardUseCase
    @Inject
    constructor(
        private val boardRepository: BoardRepository,
    ) {
        suspend fun deleteBoard(deleteBoardRequest: DeleteBoardRequest): Result<Int> = boardRepository.deleteBoard(deleteBoardRequest)
    }
