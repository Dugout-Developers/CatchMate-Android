package com.catchmate.domain.usecase.board

import com.catchmate.domain.model.PutBoardRequest
import com.catchmate.domain.model.PutBoardResponse
import com.catchmate.domain.repository.BoardRepository
import javax.inject.Inject

class PutBoardUseCase
    @Inject
    constructor(
        private val boardRepository: BoardRepository,
    ) {
        suspend fun putBoard(putBoardRequest: PutBoardRequest): Result<PutBoardResponse> = boardRepository.putBoard(putBoardRequest)
    }
