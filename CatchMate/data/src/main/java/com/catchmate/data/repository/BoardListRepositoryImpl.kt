package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardListService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardListMapper
import com.catchmate.domain.model.BoardListResponse
import com.catchmate.domain.repository.BoardListRepository
import org.json.JSONObject
import javax.inject.Inject

class BoardListRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : BoardListRepository {
        private val boardListApi = retrofitClient.createApi<BoardListService>()

        override suspend fun getBoardList(
            pageNum: Long,
            gudans: String,
            people: Int,
            gameDate: String,
        ): List<BoardListResponse>? =
            try {
                val response = boardListApi.getBoardList(pageNum, gudans, people, gameDate)
                if (response.isSuccessful) {
                    Log.d("BoardListRepository", "통신 성공")
                    response.body()?.let { BoardListMapper.toBoardListResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Log.d("BoardListRepository", "통신 실패 : ${response.code()} - $stringToJson")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
