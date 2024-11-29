package com.catchmate.domain.usecase.board

import com.catchmate.domain.model.GetLikedBoardResponse
import com.catchmate.domain.repository.BoardRepository
import javax.inject.Inject

class GetLikedBoardUseCase
    @Inject
    constructor(
        private val boardRepository: BoardRepository,
    ) {
        suspend fun getLikedBoard(): Result<List<GetLikedBoardResponse>> = boardRepository.getLikedBoard()
    }
