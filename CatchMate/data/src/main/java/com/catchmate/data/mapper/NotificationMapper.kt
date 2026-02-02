package com.catchmate.data.mapper

import com.catchmate.data.dto.notification.DeleteReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.GetHasUnreadNotificationResponseDto
import com.catchmate.data.dto.notification.GetNotificationListResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.InquiryDTO
import com.catchmate.data.dto.notification.NotificationInfoDTO
import com.catchmate.data.mapper.BoardMapper.toBoard
import com.catchmate.domain.model.notification.DeleteReceivedNotificationResponse
import com.catchmate.domain.model.notification.GetHasUnreadNotificationResponse
import com.catchmate.domain.model.notification.GetNotificationListResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationResponse
import com.catchmate.domain.model.notification.Inquiry
import com.catchmate.domain.model.notification.NotificationInfo

object NotificationMapper {
    fun toGetReceivedNotificationListResponse(responseDTO: GetNotificationListResponseDTO): GetNotificationListResponse =
        GetNotificationListResponse(
            content = responseDTO.content.map { toNotificationInfo(it) },
            pageNumber = responseDTO.pageNumber,
            totalPages = responseDTO.totalPages,
            totalElements = responseDTO.totalElements,
            hasNext = responseDTO.hasNext,
        )

    private fun toNotificationInfo(dto: NotificationInfoDTO): NotificationInfo =
        NotificationInfo(
            notificationId = dto.notificationId,
            boardInfo = dto.boardInfo?.let { toBoard(it) },
            inquiryInfo = dto.inquiryInfo?.let { toInquiry(it) },
            senderProfileImageUrl = dto.senderProfileImageUrl,
            title = dto.title,
            body = dto.body,
            createdAt = dto.createdAt,
            acceptStatus = dto.acceptStatus,
            read = dto.read,
        )

    private fun toInquiry(dto: InquiryDTO): Inquiry =
        Inquiry(
            inquiryId = dto.inquiryId,
            inquiryType = dto.inquiryType,
            content = dto.content,
            nickName = dto.nickName,
            answer = dto.answer,
            isCompleted = dto.isCompleted,
            createdAt = dto.createdAt,
        )

    fun toGetReceivedNotificationResponse(dto: GetReceivedNotificationResponseDTO): GetReceivedNotificationResponse =
        GetReceivedNotificationResponse(
            notificationId = dto.notificationId,
            boardInfo = toBoard(dto.boardInfo),
            senderProfileImageUrl = dto.senderProfileImageUrl,
            title = dto.title,
            body = dto.body,
            createdAt = dto.createdAt,
            acceptStatus = dto.acceptStatus,
            read = dto.read,
        )

    fun toDeleteReceivedNotificationResponse(responseDTO: DeleteReceivedNotificationResponseDTO): DeleteReceivedNotificationResponse =
        DeleteReceivedNotificationResponse(
            state = responseDTO.state,
        )

    fun toGetHasUnreadNotificationResponse(dto: GetHasUnreadNotificationResponseDto): GetHasUnreadNotificationResponse =
        GetHasUnreadNotificationResponse(dto.hasUnread)
}
