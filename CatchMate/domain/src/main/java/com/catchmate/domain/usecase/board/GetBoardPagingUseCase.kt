package com.catchmate.domain.usecase.board

import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.repository.BoardRepository
import javax.inject.Inject

class GetBoardPagingUseCase
    @Inject
    constructor(
        private val boardRepository: BoardRepository,
    ) {
        suspend fun getBoardPaging(
            pageNum: Long,
            gudans: String,
            people: Int,
            gameDate: String,
        ): Result<List<GetBoardPagingResponse>> = boardRepository.getBoardList(pageNum, gudans, people, gameDate)
    }
