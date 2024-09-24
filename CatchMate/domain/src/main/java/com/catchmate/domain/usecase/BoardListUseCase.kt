package com.catchmate.domain.usecase

import com.catchmate.domain.model.BoardListResponse
import com.catchmate.domain.repository.BoardListRepository
import javax.inject.Inject

class BoardListUseCase
    @Inject
    constructor(
        private val boardListRepository: BoardListRepository,
    ) {
        suspend fun getBoardList(
            accessToken: String,
            pageNum: Long,
            gudans: String,
            people: Int,
            gameDate: String,
        ): List<BoardListResponse>? = boardListRepository.getBoardList(accessToken, pageNum, gudans, people, gameDate)
    }
