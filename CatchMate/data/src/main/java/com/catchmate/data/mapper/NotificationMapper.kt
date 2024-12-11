package com.catchmate.data.mapper

import com.catchmate.data.dto.BoardInfoDTO
import com.catchmate.data.dto.ContentDTO
import com.catchmate.data.dto.DeleteReceivedNotificationResponseDTO
import com.catchmate.data.dto.GetReceivedNotificationListResponseDTO
import com.catchmate.data.dto.GetReceivedNotificationResponseDTO
import com.catchmate.domain.model.BoardInfo
import com.catchmate.domain.model.Content
import com.catchmate.domain.model.DeleteReceivedNotificationResponse
import com.catchmate.domain.model.GetReceivedNotificationListResponse
import com.catchmate.domain.model.GetReceivedNotificationResponse

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
