package com.catchmate.domain.usecase.notification

import com.catchmate.domain.model.GetReceivedNotificationResponse
import com.catchmate.domain.repository.NotificationRepository
import javax.inject.Inject

class GetReceivedNotificationUseCase
    @Inject
    constructor(
        private val notificationRepository: NotificationRepository,
    ) {
        suspend fun getReceivedNotification(notificationId: Long): Result<GetReceivedNotificationResponse> =
            notificationRepository.getReceivedNotification(notificationId)
    }
