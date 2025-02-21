package com.catchmate.data.mapper

import com.catchmate.data.dto.notification.DeleteReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationListResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.NotificationInfoDTO
import com.catchmate.data.mapper.BoardMapper.toBoard
import com.catchmate.domain.model.notification.DeleteReceivedNotificationResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationListResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationResponse
import com.catchmate.domain.model.notification.NotificationInfo

object NotificationMapper {
    fun toGetReceivedNotificationListResponse(responseDTO: GetReceivedNotificationListResponseDTO): GetReceivedNotificationListResponse =
        GetReceivedNotificationListResponse(
            notificationInfoList = responseDTO.notificationInfoList.map { toNotificationInfo(it) },
            totalPages = responseDTO.totalPages,
            totalElements = responseDTO.totalElements,
            isFirst = responseDTO.isFirst,
            isLast = responseDTO.isLast,
        )

    private fun toNotificationInfo(dto: NotificationInfoDTO): NotificationInfo =
        NotificationInfo(
            notificationId = dto.notificationId,
            boardInfo = toBoard(dto.boardInfo),
            senderProfileImageUrl = dto.senderProfileImageUrl,
            title = dto.title,
            body = dto.body,
            createdAt = dto.createdAt,
            acceptStatus = dto.acceptStatus,
            read = dto.read,
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
}
