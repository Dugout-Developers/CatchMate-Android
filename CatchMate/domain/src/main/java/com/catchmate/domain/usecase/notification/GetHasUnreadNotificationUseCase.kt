package com.catchmate.domain.usecase.notification

import com.catchmate.domain.model.notification.GetHasUnreadNotificationResponse
import com.catchmate.domain.repository.NotificationRepository
import javax.inject.Inject

class GetHasUnreadNotificationUseCase
@Inject
constructor(
    private val notificationRepository: NotificationRepository
    ) {
        suspend operator fun invoke(): Result<GetHasUnreadNotificationResponse> =
            notificationRepository.getHasUnreadNotification()
    }
