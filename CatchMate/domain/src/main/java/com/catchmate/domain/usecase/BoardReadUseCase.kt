package com.catchmate.domain.usecase

import com.catchmate.domain.model.BoardDeleteRequest
import com.catchmate.domain.repository.BoardReadRepository
import javax.inject.Inject

class BoardReadUseCase
    @Inject
    constructor(
        private val boardReadRepository: BoardReadRepository,
    ) {
        suspend fun deleteBoard(boardDeleteRequest: BoardDeleteRequest): Int? = boardReadRepository.deleteBoard(boardDeleteRequest)
    }
