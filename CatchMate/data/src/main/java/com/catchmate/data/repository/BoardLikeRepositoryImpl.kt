package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardLikeService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.domain.repository.BoardLikeRepository
import org.json.JSONObject
import javax.inject.Inject

class BoardLikeRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : BoardLikeRepository {
        private val boardLikeApi = retrofitClient.createApi<BoardLikeService>()

        override suspend fun postBoardLike(
            boardId: Long,
            flag: Int,
        ): Int? =
            try {
                val response = boardLikeApi.postBoardLike(boardId, flag)
                if (response.isSuccessful) {
                    Log.d("BoardLikeRepository", "통신 성공 : ${response.code()}")
                    response.code()
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Log.d("BoardWriteRepository", "통신 실패 : ${response.code()}\n$stringToJson")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
