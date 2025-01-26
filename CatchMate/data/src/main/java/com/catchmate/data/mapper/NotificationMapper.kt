package com.catchmate.data.mapper

import com.catchmate.data.dto.board.BoardDTO
import com.catchmate.data.dto.enroll.GameInfoDTO
import com.catchmate.data.dto.enroll.UserInfoDTO
import com.catchmate.data.dto.notification.DeleteReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationListResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.NotificationInfoDTO
import com.catchmate.data.dto.user.FavoriteClubDTO
import com.catchmate.domain.model.board.Board
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import com.catchmate.domain.model.notification.DeleteReceivedNotificationResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationListResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationResponse
import com.catchmate.domain.model.notification.NotificationInfo
import com.catchmate.domain.model.user.FavoriteClub

object NotificationMapper {
    fun toGetReceivedNotificationListResponse(responseDTO: GetReceivedNotificationListResponseDTO): GetReceivedNotificationListResponse =
        GetReceivedNotificationListResponse(
            notificationInfoList = responseDTO.notificationInfoList.map { toNotificationInfo(it) },
        )

    private fun toNotificationInfo(dto: NotificationInfoDTO): NotificationInfo =
        NotificationInfo(
            notificationId = dto.notificationId,
            boardInfo = toBoard(dto.boardInfo),
            senderProfileImageUrl = dto.senderProfileImageUrl,
            title = dto.title,
            body = dto.body,
            createdAt = dto.createdAt,
            read = dto.read,
        )

    private fun toBoard(dto: BoardDTO): Board =
        Board(
            boardId = dto.boardId,
            title = dto.title,
            content = dto.content,
            cheerClubId = dto.cheerClubId,
            currentPerson = dto.currentPerson,
            maxPerson = dto.maxPerson,
            preferredGender = dto.preferredGender,
            preferredAgeRange = dto.preferredAgeRange,
            gameInfo = toGameInfo(dto.gameInfo),
            liftUpDate = dto.liftUpDate,
            userInfo = toUserInfo(dto.userInfo),
            buttonStatus = dto.buttonStatus,
            bookMarked = dto.bookMarked,
        )

    private fun toGameInfo(dto: GameInfoDTO): GameInfo =
        GameInfo(
            homeClubId = dto.homeClubId,
            awayClubId = dto.awayClubId,
            gameStartDate = dto.gameStartDate,
            location = dto.location,
        )

    private fun toUserInfo(dto: UserInfoDTO): UserInfo =
        UserInfo(
            userId = dto.userId,
            email = dto.email,
            profileImageUrl = dto.profileImageUrl,
            gender = dto.gender,
            allAlarm = dto.allAlarm,
            chatAlarm = dto.chatAlarm,
            enrollAlarm = dto.enrollAlarm,
            eventAlarm = dto.eventAlarm,
            nickName = dto.nickName,
            favoriteClub = toFavoriteClub(dto.favoriteClub),
            birthDate = dto.birthDate,
            watchStyle = dto.watchStyle,
        )

    private fun toFavoriteClub(dto: FavoriteClubDTO): FavoriteClub =
        FavoriteClub(
            id = dto.id,
            name = dto.name,
            homeStadium = dto.homeStadium,
            region = dto.region,
        )

    fun toGetReceivedNotificationResponse(dto: GetReceivedNotificationResponseDTO): GetReceivedNotificationResponse =
        GetReceivedNotificationResponse(
            notificationId = dto.notificationId,
            boardInfo = toBoard(dto.boardInfo),
            senderProfileImageUrl = dto.senderProfileImageUrl,
            title = dto.title,
            body = dto.body,
            createdAt = dto.createdAt,
            read = dto.read,
        )

    fun toDeleteReceivedNotificationResponse(responseDTO: DeleteReceivedNotificationResponseDTO): DeleteReceivedNotificationResponse =
        DeleteReceivedNotificationResponse(
            state = responseDTO.state,
        )
}
