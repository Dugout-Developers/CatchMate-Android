package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.ChattingService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.ChattingMapper.toChatRoomInfo
import com.catchmate.data.mapper.ChattingMapper.toDeleteChattingCrewKickOutResponse
import com.catchmate.data.mapper.ChattingMapper.toGetChattingCrewListResponse
import com.catchmate.data.mapper.ChattingMapper.toGetChattingHistoryResponse
import com.catchmate.data.mapper.ChattingMapper.toGetChattingRoomListResponse
import com.catchmate.data.mapper.ChattingMapper.toDeleteChattingRoomResponse
import com.catchmate.data.mapper.ChattingMapper.toPatchChattingRoomImageResponse
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.DeleteChattingCrewKickOutResponse
import com.catchmate.domain.model.chatting.DeleteChattingRoomResponse
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.model.chatting.PatchChattingRoomImageResponse
import com.catchmate.domain.repository.ChattingRepository
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

class ChattingRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : ChattingRepository {
        private val chattingApi = retrofitClient.createApi<ChattingService>()

        override suspend fun getChattingRoomList(page: Int): Result<GetChattingRoomListResponse> =
            try {
                val response = chattingApi.getChattingRoomList(page)
                if (response.isSuccessful) {
                    Log.d("ChattingRepo", "통신 성공")
                    val body = response.body()?.let { toGetChattingRoomListResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("ChattingRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getChattingCrewList(chatRoomId: Long): Result<GetChattingCrewListResponse> =
            try {
                val response = chattingApi.getChattingCrewList(chatRoomId)
                if (response.isSuccessful) {
                    Log.d("ChattingRepo", "통신 성공")
                    val body = response.body()?.let { toGetChattingCrewListResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("ChattingRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getChattingRoomInfo(chatRoomId: Long): Result<ChatRoomInfo> =
            try {
                val response = chattingApi.getChattingRoomInfo(chatRoomId)
                if (response.isSuccessful) {
                    Log.d("ChattingRepo", "통신 성공")
                    val body = response.body()?.let { toChatRoomInfo(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("ChattingRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun patchChattingRoomImage(
            chatRoomId: Long,
            chatRoomImage: MultipartBody.Part,
        ): Result<PatchChattingRoomImageResponse> =
            try {
                val response = chattingApi.patchChattingRoomImage(chatRoomId, chatRoomImage)
                if (response.isSuccessful) {
                    Log.d("ChattingRepo", "통신 성공")
                    val body = response.body()?.let { toPatchChattingRoomImageResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("ChattingRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun deleteChattingRoom(chatRoomId: Long): Result<DeleteChattingRoomResponse> =
            try {
                val response = chattingApi.deleteChattingRoom(chatRoomId)
                if (response.isSuccessful) {
                    Log.d("ChattingRepo", "통신 성공")
                    val body = response.body()?.let { toDeleteChattingRoomResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("ChattingRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun deleteChattingCrewKickOut(
            chatRoomId: Long,
            userId: Long,
        ): Result<DeleteChattingCrewKickOutResponse> =
            try {
                val response = chattingApi.deleteChattingCrewKickOut(chatRoomId, userId)
                if (response.isSuccessful) {
                    Log.d("ChattingRepo", "통신 성공")
                    val body = response.body()?.let { toDeleteChattingCrewKickOutResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("ChattingRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getChattingHistory(
            chatRoomId: Long,
            page: Int,
            size: Int?,
        ): Result<GetChattingHistoryResponse> =
            try {
                val response = chattingApi.getChattingHistory(chatRoomId, page, size)
                if (response.isSuccessful) {
                    Log.d("ChattingRepo", "통신 성공")
                    val body = response.body()?.let { toGetChattingHistoryResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("ChattingRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
