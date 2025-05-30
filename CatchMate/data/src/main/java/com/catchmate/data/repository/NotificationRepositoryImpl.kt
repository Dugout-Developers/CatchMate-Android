package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.NotificationService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.NotificationMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.notification.DeleteReceivedNotificationResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationListResponse
import com.catchmate.domain.model.notification.GetReceivedNotificationResponse
import com.catchmate.domain.repository.NotificationRepository
import org.json.JSONObject
import javax.inject.Inject

class NotificationRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : NotificationRepository {
        private val notificationApi = retrofitClient.createApi<NotificationService>()

        override suspend fun getReceivedNotificationList(page: Int): Result<GetReceivedNotificationListResponse> =
            try {
                val response = notificationApi.getReceivedNotificationList(page)
                if (response.isSuccessful) {
                    Log.d("NotiRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                NotificationMapper.toGetReceivedNotificationListResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("NotiRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getReceivedNotification(notificationId: Long): Result<GetReceivedNotificationResponse> =
            try {
                val response = notificationApi.getReceivedNotification(notificationId)
                if (response.isSuccessful) {
                    Log.d("NotiRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                NotificationMapper.toGetReceivedNotificationResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("NotiRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun deleteReceivedNotification(notificationId: Long): Result<DeleteReceivedNotificationResponse> =
            try {
                val response = notificationApi.deleteReceivedNotification(notificationId)
                if (response.isSuccessful) {
                    Log.d("NotiRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                NotificationMapper.toDeleteReceivedNotificationResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("NotiRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
