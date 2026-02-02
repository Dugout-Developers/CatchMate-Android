package com.catchmate.data.repository

import com.catchmate.data.datasource.remote.NotificationService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.NotificationMapper
import com.catchmate.data.util.ApiResponseHandleUtil.apiCall
import com.catchmate.domain.model.notification.DeleteReceivedNotificationResponse
import com.catchmate.domain.model.notification.GetNotificationListResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationResponse
import com.catchmate.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : NotificationRepository {
        private val notificationApi = retrofitClient.createApi<NotificationService>()
        private val tag = "NotificationRepo"

        override suspend fun getNotificationList(
            page: Int,
            size: Int,
        ): Result<GetNotificationListResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { notificationApi.getNotificationList(page, size) },
                transform = { NotificationMapper.toGetReceivedNotificationListResponse(it!!) },
            )

        override suspend fun getReceivedNotification(notificationId: Long): Result<GetReceivedNotificationResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { notificationApi.getReceivedNotification(notificationId) },
                transform = { NotificationMapper.toGetReceivedNotificationResponse(it!!) },
            )

        override suspend fun deleteReceivedNotification(notificationId: Long): Result<DeleteReceivedNotificationResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { notificationApi.deleteReceivedNotification(notificationId) },
                transform = { NotificationMapper.toDeleteReceivedNotificationResponse(it!!) },
            )
    }
