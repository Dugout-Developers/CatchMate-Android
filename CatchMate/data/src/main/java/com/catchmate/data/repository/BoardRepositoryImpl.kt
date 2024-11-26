package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.model.PostBoardRequest
import com.catchmate.domain.model.PostBoardResponse
import com.catchmate.domain.model.PutBoardRequest
import com.catchmate.domain.model.PutBoardResponse
import com.catchmate.domain.repository.BoardRepository
import org.json.JSONObject
import javax.inject.Inject

class BoardRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : BoardRepository {
        private val boardApi = retrofitClient.createApi<BoardService>()

        override suspend fun postBoard(postBoardRequest: PostBoardRequest): Result<PostBoardResponse> =
            try {
                val response = boardApi.postBoard(BoardMapper.toPostBoardRequestDTO(postBoardRequest))
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { BoardMapper.toPostBoardResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("BoardRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun putBoard(putBoardRequest: PutBoardRequest): Result<PutBoardResponse> =
            try {
                val response = boardApi.putBoard(BoardMapper.toPutBoardRequestDTO(putBoardRequest))
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { BoardMapper.toPutBoardResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("BoardRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getBoardList(
            pageNum: Long,
            gudans: String,
            people: Int,
            gameDate: String
        ): Result<List<GetBoardPagingResponse>> =
            try {
                val response = boardApi.getBoardPaging(pageNum, gudans, people, gameDate)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body = response.body()?.let { BoardMapper.toGetBoardPagingResponse(it) }
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
