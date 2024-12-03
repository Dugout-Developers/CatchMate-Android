package com.catchmate.domain.repository

import com.catchmate.domain.model.GetReceivedNotificationListResponse

interface NotificationRepository {
    suspend fun getReceivedNotificationList(): Result<GetReceivedNotificationListResponse>
}
