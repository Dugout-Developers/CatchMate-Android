package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardWriteService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardWriteMapper
import com.catchmate.domain.model.BoardWriteRequest
import com.catchmate.domain.model.BoardWriteResponse
import com.catchmate.domain.repository.BoardWriteRepository
import javax.inject.Inject

class BoardWriteRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ): BoardWriteRepository {
        private val boardWriteApi = retrofitClient.createApi<BoardWriteService>()

        override suspend fun postBoardWrite(
            accessToken: String,
            boardWriteRequest: BoardWriteRequest,
        ): BoardWriteResponse? =
            try {
                val response = boardWriteApi.postBoardWrite(accessToken, BoardWriteMapper.toBoardWriteRequestDTO(boardWriteRequest))
                if (response.isSuccessful) {
                    Log.d("BoardWriteRepository", "통신 성공 : ${response.code()}")
                    response.body()?.let { BoardWriteMapper.toBoardWriteResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    Log.d("BoardWriteRepository", "통신 실패 : ${response.code()} - ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
