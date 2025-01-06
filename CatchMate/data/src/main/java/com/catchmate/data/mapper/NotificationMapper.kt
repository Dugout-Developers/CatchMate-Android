package com.catchmate.data.mapper

import com.catchmate.data.dto.notification.BoardInfoDTO
import com.catchmate.data.dto.notification.ContentDTO
import com.catchmate.data.dto.notification.DeleteReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationListResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationResponseDTO
import com.catchmate.domain.model.notification.BoardInfo
import com.catchmate.domain.model.notification.Content
import com.catchmate.domain.model.notification.DeleteReceivedNotificationResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationListResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationResponse

object NotificationMapper {
    fun toGetReceivedNotificationListResponse(responseDTO: GetReceivedNotificationListResponseDTO): GetReceivedNotificationListResponse =
        GetReceivedNotificationListResponse(
            content = responseDTO.content.map { toContent(it) },
        )

    private fun toContent(contentDTO: ContentDTO): Content =
        Content(
            notificationId = contentDTO.notificationId,
            boardInfo = toBoardInfo(contentDTO.boardInfo),
            title = contentDTO.title,
            body = contentDTO.body,
            createdAt = contentDTO.createdAt,
            read = contentDTO.read,
        )

    private fun toBoardInfo(boardInfoDTO: BoardInfoDTO): BoardInfo =
        BoardInfo(
            boardId = boardInfoDTO.boardId,
            title = boardInfoDTO.title,
            gameDate = boardInfoDTO.gameDate,
            location = boardInfoDTO.location,
            homeTeam = boardInfoDTO.homeTeam,
            awayTeam = boardInfoDTO.awayTeam,
            cheerTeam = boardInfoDTO.cheerTeam,
            currentPerson = boardInfoDTO.currentPerson,
            maxPerson = boardInfoDTO.maxPerson,
            addInfo = boardInfoDTO.addInfo,
        )

    fun toGetReceivedNotificationResponse(responseDTO: GetReceivedNotificationResponseDTO): GetReceivedNotificationResponse =
        GetReceivedNotificationResponse(
            notificationID = responseDTO.notificationID,
            boardInfo = toBoardInfo(responseDTO.boardInfo),
            title = responseDTO.title,
            body = responseDTO.body,
            createdAt = responseDTO.createdAt,
            read = responseDTO.read,
        )

    fun toDeleteReceivedNotificationResponse(responseDTO: DeleteReceivedNotificationResponseDTO): DeleteReceivedNotificationResponse =
        DeleteReceivedNotificationResponse(
            state = responseDTO.state,
        )
}
