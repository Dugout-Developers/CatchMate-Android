package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardLikeService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardListMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.repository.BoardLikeRepository
import org.json.JSONObject
import javax.inject.Inject

class BoardLikeRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : BoardLikeRepository {
        private val boardLikeApi = retrofitClient.createApi<BoardLikeService>()
        override suspend fun getBoardLikedList(): Result<List<GetBoardPagingResponse>> =
            try {
                val response = boardLikeApi.getBoardLikedList()
                if (response.isSuccessful) {
                    Log.d("BoardLikeRepository", "통신 성공")
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
