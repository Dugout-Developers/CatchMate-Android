package com.catchmate.domain.usecase

import com.catchmate.domain.model.BoardWriteRequest
import com.catchmate.domain.model.BoardWriteResponse
import com.catchmate.domain.repository.BoardWriteRepository
import javax.inject.Inject

class BoardWriteUseCase
    @Inject
    constructor(
        private val boardWriteRepository: BoardWriteRepository,
    ) {
        suspend fun postBoardWrite(
            accessToken: String,
            boardWriteRequest: BoardWriteRequest,
        ): BoardWriteResponse? = boardWriteRepository.postBoardWrite(accessToken, boardWriteRequest)
    }
