package com.catchmate.domain.repository

import com.catchmate.domain.model.EnrollCancelResponse
import com.catchmate.domain.model.EnrollRequest
import com.catchmate.domain.model.EnrollResponse

interface EnrollRepository {
    suspend fun postEnroll(
        boardId: Long,
        enrollRequest: EnrollRequest,
    ): EnrollResponse?

    suspend fun postEnrollCancel(
        enrollId: Long,
    ): EnrollCancelResponse?
}
