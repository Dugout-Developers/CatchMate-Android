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

interface SupportService {
    @GET("inquiries/{inquiryId}")
    suspend fun getInquiry(
        @Path("inquiryId") inquiryId: Long,
    ): Response<GetInquiryResponseDTO?>

    @POST("inquiries")
    suspend fun postInquiry(
        @Body postInquiryRequestDTO: PostInquiryRequestDTO,
    ): Response<PostInquiryResponseDTO?>

    @POST("reports/{reportedUserId}")
    suspend fun postUserReport(
        @Path("reportedUserId") reportedUserId: Long,
        @Body postUserReportRequestDTO: PostUserReportRequestDTO,
    ): Response<PostUserReportResponseDTO?>

    @GET("notices/list")
    suspend fun getNoticeList(): Response<GetNoticeListResponseDTO?>

    @GET("notices/{noticeId}")
    suspend fun getNoticeDetail(
        @Path("noticeId") noticeId: Long,
    ): Response<NoticeInfoDTO?>
}
