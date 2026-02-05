package com.catchmate.data.repository

import com.catchmate.data.datasource.remote.EnrollService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.EnrollMapper
import com.catchmate.data.util.ApiResponseHandleUtil.apiCall
import com.catchmate.domain.model.enroll.DeleteEnrollResponse
import com.catchmate.domain.model.enroll.GetAllReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetEnrollNewCountResponse
import com.catchmate.domain.model.enroll.GetReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetRequestedEnrollListResponse
import com.catchmate.domain.model.enroll.GetEnrollResponse
import com.catchmate.domain.model.enroll.PatchEnrollAcceptResponse
import com.catchmate.domain.model.enroll.PatchEnrollRejectResponse
import com.catchmate.domain.model.enroll.PostEnrollRequest
import com.catchmate.domain.model.enroll.PostEnrollResponse
import com.catchmate.domain.repository.EnrollRepository
import javax.inject.Inject

class EnrollRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : EnrollRepository {
        private val enrollApi = retrofitClient.createApi<EnrollService>()
        private val tag = "EnrollRepo"

        override suspend fun postEnroll(
            boardId: Long,
            postEnrollRequest: PostEnrollRequest,
        ): Result<PostEnrollResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.postEnroll(boardId, EnrollMapper.toPostEnrollRequestDTO(postEnrollRequest)) },
                transform = { EnrollMapper.toPostEnrollResponse(it!!) },
            )

        override suspend fun patchEnrollReject(enrollId: Long): Result<PatchEnrollRejectResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.patchEnrollReject(enrollId) },
                transform = { EnrollMapper.toPatchEnrollRejectResponse(it!!) },
            )

        override suspend fun patchEnrollAccept(enrollId: Long): Result<PatchEnrollAcceptResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.patchEnrollAccept(enrollId) },
                transform = { EnrollMapper.toPatchEnrollAcceptResponse(it!!) },
            )

        override suspend fun getEnroll(enrollId: Long): Result<GetEnrollResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.getEnroll(enrollId) },
                transform = { EnrollMapper.toGetEnrollResponse(it!!) },
            )

        override suspend fun getRequestedEnrollList(
            page: Int,
            size: Int,
        ): Result<GetRequestedEnrollListResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.getRequestedEnrollList(page, size) },
                transform = { EnrollMapper.toGetRequestedEnrollListResponse(it!!) },
            )

        override suspend fun getReceivedEnroll(boardId: Long): Result<GetReceivedEnrollResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.getReceivedEnroll(boardId) },
                transform = { EnrollMapper.toGetReceivedEnrollResponse(it!!) },
            )

        override suspend fun getAllReceivedEnroll(
            page: Int,
            size: Int,
        ): Result<GetAllReceivedEnrollResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.getAllReceivedEnroll(page, size) },
                transform = { EnrollMapper.toGetAllReceivedEnrollResponse(it!!) },
            )

        override suspend fun getEnrollNewCount(): Result<GetEnrollNewCountResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.getEnrollNewCount() },
                transform = { EnrollMapper.toGetEnrollNewCountResponse(it!!) },
            )

        override suspend fun deleteEnroll(enrollId: Long): Result<DeleteEnrollResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { enrollApi.deleteEnroll(enrollId) },
                transform = { EnrollMapper.toDeleteEnrollResponse(it!!) },
            )
    }
