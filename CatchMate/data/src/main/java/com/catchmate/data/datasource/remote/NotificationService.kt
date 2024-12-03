package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.GetReceivedNotificationListResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface NotificationService {
    @GET("notification/receive")
    suspend fun getReceivedNotificationList(): Response<GetReceivedNotificationListResponseDTO>
}
