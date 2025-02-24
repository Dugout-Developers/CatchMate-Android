package com.catchmate.data.mapper

import com.catchmate.data.dto.support.PostInquiryRequestDTO
import com.catchmate.data.dto.support.PostInquiryResponseDTO
import com.catchmate.data.dto.support.PostUserReportRequestDTO
import com.catchmate.data.dto.support.PostUserReportResponseDTO
import com.catchmate.domain.model.support.PostInquiryRequest
import com.catchmate.domain.model.support.PostInquiryResponse
import com.catchmate.domain.model.support.PostUserReportRequest
import com.catchmate.domain.model.support.PostUserReportResponse

object SupportMapper {
    fun toPostInquiryRequestDTO(request: PostInquiryRequest): PostInquiryRequestDTO =
        PostInquiryRequestDTO(
            inquiryType = request.inquiryType,
            content = request.content,
        )

    fun toPostInquiryResponse(dto: PostInquiryResponseDTO): PostInquiryResponse =
        PostInquiryResponse(
            state = dto.state,
        )

    fun toPostUserReportRequestDTO(request: PostUserReportRequest): PostUserReportRequestDTO =
        PostUserReportRequestDTO(
            reportedUserId = request.reportedUserId,
            reportType = request.reportType,
            content = request.content,
        )

    fun toPostUserReportResponse(dto: PostUserReportResponseDTO): PostUserReportResponse =
        PostUserReportResponse(
            state = dto.state,
        )
}
