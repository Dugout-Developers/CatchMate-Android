package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardReadService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardReadMapper
import com.catchmate.domain.model.BoardDeleteRequest
import com.catchmate.domain.model.BoardReadResponse
import com.catchmate.domain.repository.BoardReadRepository
import org.json.JSONObject
import javax.inject.Inject

class BoardReadRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : BoardReadRepository {
        private val boardReadApi = retrofitClient.createApi<BoardReadService>()

        override suspend fun getBoard(boardId: Long): BoardReadResponse? =
            try {
                val response = boardReadApi.getBoard(boardId)
                if (response.isSuccessful) {
                    Log.d("BoardRead", "통신 성공 : ${response.code()}")
                    response.body()?.let { BoardReadMapper.toBoardReadResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Log.d("BoardRead", "통신 실패 : ${response.code()} - $stringToJson")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        override suspend fun deleteBoard(boardDeleteRequest: BoardDeleteRequest): Int? =
            try {
                val response = boardReadApi.deleteBoard(BoardReadMapper.toBoardDeleteRequestDTO(boardDeleteRequest))
                if (response.isSuccessful) {
                    Log.d("BoardDelete", "통신 성공")
                    response.code()
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Log.d("BoardDelete", "통신 실패 : ${response.code()} - $stringToJson")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
