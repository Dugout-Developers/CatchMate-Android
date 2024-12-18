package com.catchmate.domain.usecase.enroll

import com.catchmate.domain.model.GetRequestedEnrollListResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class GetRequestedEnrollListUseCase
    @Inject
    constructor(
        private val enrollRepository: EnrollRepository,
    ) {
        suspend fun getRequestedEnrollList(): Result<GetRequestedEnrollListResponse> = enrollRepository.getRequestedEnrollList()
    }
