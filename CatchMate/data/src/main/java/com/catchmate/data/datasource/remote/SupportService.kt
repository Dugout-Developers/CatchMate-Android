package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.support.GetInquiryResponseDTO
import com.catchmate.data.dto.support.GetNoticeListResponseDTO
import com.catchmate.data.dto.support.NoticeInfoDTO
import com.catchmate.data.dto.support.PostInquiryRequestDTO
import com.catchmate.data.dto.support.PostInquiryResponseDTO
import com.catchmate.data.dto.support.PostUserReportRequestDTO
import com.catchmate.data.dto.support.PostUserReportResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SupportService {
    @GET("inquiries/{inquiryId}")
    suspend fun getInquiry(
        @Path("inquiryId") inquiryId: Long,
    ): Response<GetInquiryResponseDTO?>

    @POST("api/inquiries")
    suspend fun postInquiry(
        @Body postInquiryRequestDTO: PostInquiryRequestDTO,
    ): Response<PostInquiryResponseDTO?>

    @POST("api/reports")
    suspend fun postUserReport(
        @Body postUserReportRequestDTO: PostUserReportRequestDTO,
    ): Response<PostUserReportResponseDTO?>

    @GET("api/notices")
    suspend fun getNoticeList(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<GetNoticeListResponseDTO?>

    @GET("notices/{noticeId}")
    suspend fun getNoticeDetail(
        @Path("noticeId") noticeId: Long,
    ): Response<NoticeInfoDTO?>
}
