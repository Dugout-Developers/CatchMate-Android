package com.catchmate.data.repository

import com.catchmate.data.datasource.remote.NotificationService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.NotificationMapper
import com.catchmate.data.util.ApiResponseHandleUtil.apiCall
import com.catchmate.data.util.ApiResponseHandleUtil.apiCallWithFullResponse
import com.catchmate.domain.model.notification.GetHasUnreadNotificationResponse
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

        override suspend fun getHasUnreadNotification(): Result<GetHasUnreadNotificationResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { notificationApi.getHasUnreadNotification() },
                transform = { NotificationMapper.toGetHasUnreadNotificationResponse(it!!) },
            )

        override suspend fun getReceivedNotification(notificationId: Long): Result<GetReceivedNotificationResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { notificationApi.getReceivedNotification(notificationId) },
                transform = { NotificationMapper.toGetReceivedNotificationResponse(it!!) },
            )

        override suspend fun deleteNotification(notificationId: Long): Result<Int> =
            apiCallWithFullResponse(
                tag = this.tag,
                apiFunction = { notificationApi.deleteNotification(notificationId) },
                transform = { it.code() },
            )
    }
