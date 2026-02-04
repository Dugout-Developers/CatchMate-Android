package com.catchmate.domain.repository

import com.catchmate.domain.model.notification.GetHasUnreadNotificationResponse
import com.catchmate.domain.model.notification.GetNotificationListResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationResponse

interface NotificationRepository {
    // 받은 알림 목록 get
    suspend fun getNotificationList(
        page: Int,
        size: Int,
    ): Result<GetNotificationListResponse>

    suspend fun getHasUnreadNotification(): Result<GetHasUnreadNotificationResponse>

    // 알림 상세 get
    suspend fun getReceivedNotification(notificationId: Long): Result<GetReceivedNotificationResponse>

    suspend fun deleteNotification(notificationId: Long): Result<Int>
}
