package com.catchmate.data.mapper

import com.catchmate.data.dto.support.GetInquiryResponseDTO
import com.catchmate.data.dto.support.GetNoticeListResponseDTO
import com.catchmate.data.dto.support.NoticeInfoDTO
import com.catchmate.data.dto.support.PostInquiryRequestDTO
import com.catchmate.data.dto.support.PostInquiryResponseDTO
import com.catchmate.data.dto.support.PostUserReportRequestDTO
import com.catchmate.data.dto.support.PostUserReportResponseDTO
import com.catchmate.data.mapper.BoardMapper.toClub
import com.catchmate.domain.model.support.GetInquiryResponse
import com.catchmate.domain.model.support.GetNoticeListResponse
import com.catchmate.domain.model.support.NoticeInfo
import com.catchmate.domain.model.support.PostInquiryRequest
import com.catchmate.domain.model.support.PostInquiryResponse
import com.catchmate.domain.model.support.PostUserReportRequest
import com.catchmate.domain.model.support.PostUserReportResponse

object SupportMapper {
    fun toPostInquiryRequestDTO(request: PostInquiryRequest): PostInquiryRequestDTO =
        PostInquiryRequestDTO(
            type = request.type,
            content = request.content,
        )

    fun toPostInquiryResponse(dto: PostInquiryResponseDTO): PostInquiryResponse =
        PostInquiryResponse(
            inquiryId = dto.inquiryId,
            createdAt = dto.createdAt,
        )

    fun toPostUserReportRequestDTO(request: PostUserReportRequest): PostUserReportRequestDTO =
        PostUserReportRequestDTO(
            reportedUserId = request.reportedUserId,
            reason = request.reason,
            description = request.description,
        )

    fun toPostUserReportResponse(dto: PostUserReportResponseDTO): PostUserReportResponse =
        PostUserReportResponse(
            reportId = dto.reportId,
            createdAt = dto.createdAt,
        )

    fun toGetInquiryResponse(dto: GetInquiryResponseDTO): GetInquiryResponse =
        GetInquiryResponse(
            inquiryId = dto.inquiryId,
            inquiryType = dto.inquiryType,
            content = dto.content,
            nickName = dto.nickName,
            answer = dto.answer,
            isCompleted = dto.isCompleted,
            createdAt = dto.createdAt,
        )

    fun toGetNoticeListResponse(dto: GetNoticeListResponseDTO): GetNoticeListResponse =
        GetNoticeListResponse(
            content = dto.content.map { toNoticeInfo(it) },
            pageNumber = dto.pageNumber,
            totalPages = dto.totalPages,
            totalElements = dto.totalElements,
            hasNext = dto.hasNext,
        )

    fun toNoticeInfo(dto: NoticeInfoDTO): NoticeInfo =
        NoticeInfo(
            noticeId = dto.noticeId,
            title = dto.title,
            writerNickname = dto.writerNickname,
            createdAt = dto.createdAt,
        )
}
