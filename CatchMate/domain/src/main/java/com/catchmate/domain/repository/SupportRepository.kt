package com.catchmate.domain.repository

import com.catchmate.domain.model.support.GetInquiryResponse
import com.catchmate.domain.model.support.PostInquiryRequest
import com.catchmate.domain.model.support.PostInquiryResponse
import com.catchmate.domain.model.support.PostUserReportRequest
import com.catchmate.domain.model.support.PostUserReportResponse

interface SupportRepository {
    suspend fun getInquiry(inquiryId: Long): Result<GetInquiryResponse>

    suspend fun postInquiry(request: PostInquiryRequest): Result<PostInquiryResponse>

    suspend fun portUserReport(request: PostUserReportRequest): Result<PostUserReportResponse>
}
