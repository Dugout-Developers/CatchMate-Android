package com.catchmate.domain.repository

import com.catchmate.domain.model.support.PostInquiryRequest
import com.catchmate.domain.model.support.PostInquiryResponse

interface SupportRepository {
    suspend fun postInquiry(request: PostInquiryRequest): Result<PostInquiryResponse>
}
