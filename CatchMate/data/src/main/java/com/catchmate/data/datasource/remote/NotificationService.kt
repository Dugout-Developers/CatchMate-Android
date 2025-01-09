package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.notification.DeleteReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationListResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationResponseDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {
    @GET("notification/receive")
    suspend fun getReceivedNotificationList(): Response<GetReceivedNotificationListResponseDTO>

    @GET("notification/receive/{notificationId}")
    suspend fun getReceivedNotification(
        @Path("notificationId") notificationId: Long,
    ): Response<GetReceivedNotificationResponseDTO>

    @DELETE("notification/receive/{notificationId}")
    suspend fun deleteReceivedNotification(
        @Path("notificationId") notificationId: Long,
    ): Response<DeleteReceivedNotificationResponseDTO>
}
