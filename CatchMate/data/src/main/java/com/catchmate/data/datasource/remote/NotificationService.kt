package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.notification.DeleteReceivedNotificationResponseDTO
import com.catchmate.data.dto.notification.GetHasUnreadNotificationResponseDto
import com.catchmate.data.dto.notification.GetNotificationListResponseDTO
import com.catchmate.data.dto.notification.GetReceivedNotificationResponseDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationService {
    @GET("api/notifications")
    suspend fun getNotificationList(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<GetNotificationListResponseDTO?>

    @GET("api/notifications/unread")
    suspend fun getHasUnreadNotification(): Response<GetHasUnreadNotificationResponseDto>

    @GET("notifications/receive/{notificationId}")
    suspend fun getReceivedNotification(
        @Path("notificationId") notificationId: Long,
    ): Response<GetReceivedNotificationResponseDTO?>

    @DELETE("api/notifications/{notificationId}")
    suspend fun deleteNotification(
        @Path("notificationId") notificationId: Long,
    ): Response<Unit>
}
