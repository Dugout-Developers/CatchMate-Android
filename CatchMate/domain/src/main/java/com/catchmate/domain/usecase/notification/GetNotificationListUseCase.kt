package com.catchmate.domain.usecase.notification

import com.catchmate.domain.model.notification.GetNotificationListResponse
import com.catchmate.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationListUseCase
    @Inject
    constructor(
        private val notificationRepository: NotificationRepository,
    ) {
        suspend operator fun invoke(
            page: Int,
            size: Int,
        ): Result<GetNotificationListResponse> =
            notificationRepository.getNotificationList(page, size)
    }
