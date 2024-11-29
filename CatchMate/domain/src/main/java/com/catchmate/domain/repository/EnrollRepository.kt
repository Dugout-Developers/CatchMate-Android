package com.catchmate.domain.repository

import com.catchmate.domain.model.EnrollCancelResponse
import com.catchmate.domain.model.PostEnrollRequest
import com.catchmate.domain.model.PostEnrollResponse

interface EnrollRepository {
    suspend fun postEnroll(
        boardId: Long,
        postEnrollRequest: PostEnrollRequest,
    ): Result<PostEnrollResponse>

    suspend fun postEnrollCancel(enrollId: Long): EnrollCancelResponse?
}
