package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.support.PostInquiryRequestDTO
import com.catchmate.data.dto.support.PostInquiryResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SupportService {
    @POST("inquiries")
    suspend fun postInquiry(
        @Body postInquiryRequestDTO: PostInquiryRequestDTO,
    ): Response<PostInquiryResponseDTO?>
}
