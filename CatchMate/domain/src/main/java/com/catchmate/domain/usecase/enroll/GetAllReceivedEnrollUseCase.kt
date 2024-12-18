package com.catchmate.domain.usecase.enroll

import com.catchmate.domain.model.GetAllReceivedEnrollResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class GetAllReceivedEnrollUseCase
    @Inject
    constructor(
        private val enrollRepository: EnrollRepository,
    ) {
        suspend fun getAllReceivedEnroll(): Result<GetAllReceivedEnrollResponse> = enrollRepository.getAllReceivedEnroll()
    }
