package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.ChattingService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.ChattingMapper.toGetChattingRoomListResponse
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.repository.ChattingRepository
import org.json.JSONObject
import javax.inject.Inject

class ChattingRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : ChattingRepository {
        private val chattingApi = retrofitClient.createApi<ChattingService>()

        override suspend fun getChattingRoomList(): Result<GetChattingRoomListResponse> =
            try {
                val response = chattingApi.getChattingRoomList()
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
    }
