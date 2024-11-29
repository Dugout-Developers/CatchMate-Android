package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.DeleteBoardRequest
import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.model.GetBoardResponse
import com.catchmate.domain.model.GetLikedBoardResponse
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

        override suspend fun postBoardLike(boardId: Long, flag: Int): Result<Int> =
            try {
                val response = boardApi.postBoardLike(boardId, flag)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    Result.success(response.code())
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

        override suspend fun getBoardPaging(
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

        override suspend fun getBoard(boardId: Long): Result<GetBoardResponse> =
            try {
                val response = boardApi.getBoard(boardId)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body = response.body()?.let { BoardMapper.toGetBoardResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getLikedBoard(): Result<List<GetLikedBoardResponse>> =
            try {
                val response = boardApi.getLikedBoard()
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body = response.body()?.let { BoardMapper.toGetLikedBoardResponse(it) }
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

        override suspend fun deleteBoard(deleteBoardRequest: DeleteBoardRequest): Result<Int> =
            try {
                val response = boardApi.deleteBoard(BoardMapper.toDeleteBoardRequestDTO(deleteBoardRequest))
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    Result.success(response.code())
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
