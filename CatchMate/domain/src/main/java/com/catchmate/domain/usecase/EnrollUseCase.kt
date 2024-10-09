package com.catchmate.domain.usecase

import com.catchmate.domain.model.EnrollCancelResponse
import com.catchmate.domain.model.EnrollRequest
import com.catchmate.domain.model.EnrollResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class EnrollUseCase
    @Inject
    constructor(
        private val enrollRepository: EnrollRepository,
    ) {
        suspend fun postEnroll(
            boardId: Long,
            enrollRequest: EnrollRequest,
        ): EnrollResponse? =
            enrollRepository.postEnroll(boardId, enrollRequest)

        suspend fun postEnrollCancel(
            enrollId: Long,
        ): EnrollCancelResponse? =
            enrollRepository.postEnrollCancel(enrollId)
    }
