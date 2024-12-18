package com.catchmate.domain.repository

import com.catchmate.domain.model.GetReceivedEnrollResponse
import com.catchmate.domain.model.GetRequestedEnrollListResponse
import com.catchmate.domain.model.PatchEnrollAcceptResponse
import com.catchmate.domain.model.PatchEnrollRejectResponse
import com.catchmate.domain.model.PostEnrollRequest
import com.catchmate.domain.model.PostEnrollResponse

interface EnrollRepository {
    suspend fun postEnroll(
        boardId: Long,
        postEnrollRequest: PostEnrollRequest,
    ): Result<PostEnrollResponse>

    suspend fun patchEnrollReject(enrollId: Long): Result<PatchEnrollRejectResponse>

    suspend fun patchEnrollAccept(enrollId: Long): Result<PatchEnrollAcceptResponse>

    suspend fun getRequestedEnrollList(): Result<GetRequestedEnrollListResponse>

    suspend fun getReceivedEnroll(boardId: Long): Result<GetReceivedEnrollResponse>
}
