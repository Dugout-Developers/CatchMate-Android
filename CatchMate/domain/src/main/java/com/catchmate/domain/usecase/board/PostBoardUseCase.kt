package com.catchmate.domain.usecase.board

import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse
import com.catchmate.domain.repository.BoardRepository
import javax.inject.Inject

class PostBoardUseCase
    @Inject
    constructor(
        private val boardRepository: BoardRepository,
    ) {
        suspend fun postBoard(postBoardRequest: PostBoardRequest): Result<PostBoardResponse> =
            boardRepository.postBoard(postBoardRequest)
    }
