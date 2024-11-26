package com.catchmate.domain.usecase

import com.catchmate.domain.model.BoardEditRequest
import com.catchmate.domain.model.PostBoardResponse
import com.catchmate.domain.repository.BoardWriteRepository
import javax.inject.Inject

class BoardWriteUseCase
    @Inject
    constructor(
        private val boardWriteRepository: BoardWriteRepository,
    ) {
        suspend fun putBoard(boardEditRequest: BoardEditRequest): PostBoardResponse? = boardWriteRepository.putBoard(boardEditRequest)
    }
