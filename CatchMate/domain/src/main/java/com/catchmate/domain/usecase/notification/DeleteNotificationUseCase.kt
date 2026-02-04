package com.catchmate.domain.usecase.notification

import com.catchmate.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationUseCase
    @Inject
    constructor(
        private val notificationRepository: NotificationRepository,
    ) {
        suspend operator fun invoke(notificationId: Long): Result<Int> =
            notificationRepository.deleteNotification(notificationId)
    }
