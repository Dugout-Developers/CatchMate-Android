package com.catchmate.domain.usecase.enroll

import com.catchmate.domain.model.enroll.GetEnrollResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class GetEnrollUseCase
    @Inject
    constructor(
        private val enrollRepository: EnrollRepository,
    ) {
        suspend operator fun invoke(enrollId: Long): Result<GetEnrollResponse> = enrollRepository.getEnroll(enrollId)
    }
