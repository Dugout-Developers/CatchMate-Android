package com.catchmate.domain.usecase

import com.catchmate.domain.model.EnrollCancelResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class EnrollUseCase
    @Inject
    constructor(
        private val enrollRepository: EnrollRepository,
    ) {
        suspend fun postEnrollCancel(enrollId: Long): EnrollCancelResponse? = enrollRepository.postEnrollCancel(enrollId)
    }
