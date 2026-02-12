package com.catchmate.domain.usecase.enroll

import com.catchmate.domain.model.enroll.GetReceivedEnrollResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class GetReceivedEnrollUseCase
    @Inject
    constructor(
        private val enrollRepository: EnrollRepository,
    ) {
        suspend fun getReceivedEnroll(
            boardId: Long,
            page: Int,
            size: Int,
        ): Result<GetReceivedEnrollResponse> = enrollRepository.getReceivedEnroll(boardId, page, size)
    }
