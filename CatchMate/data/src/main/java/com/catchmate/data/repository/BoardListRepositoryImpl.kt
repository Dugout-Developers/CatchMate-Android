package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardListService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardListMapper
import com.catchmate.domain.exception.ReissueFailureException
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
        ): Result<List<BoardListResponse>> =
            try {
                val response = boardListApi.getBoardList(pageNum, gudans, people, gameDate)
                if (response.isSuccessful) {
                    Log.d("BoardListRepository", "통신 성공")
                    val body = response.body()?.let { BoardListMapper.toBoardListResponse(it) }
                    Result.success(body ?: emptyList())
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
