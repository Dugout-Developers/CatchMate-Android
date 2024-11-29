package com.catchmate.domain.usecase.enroll

import com.catchmate.domain.model.PostEnrollRequest
import com.catchmate.domain.model.PostEnrollResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class PostEnrollUseCase
    @Inject
    constructor(
        private val enrollRepository: EnrollRepository,
    ) {
        suspend fun postEnroll(
            boardId: Long,
            enrollRequest: PostEnrollRequest,
        ): Result<PostEnrollResponse> = enrollRepository.postEnroll(boardId, enrollRequest)
    }
