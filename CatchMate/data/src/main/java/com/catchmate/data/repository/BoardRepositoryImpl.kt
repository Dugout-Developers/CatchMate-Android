package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.board.DeleteBoardRequest
import com.catchmate.domain.model.board.GetBoardListResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
import com.catchmate.domain.model.board.PatchBoardRequest
import com.catchmate.domain.model.board.PatchBoardResponse
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

        override suspend fun postBoardLike(
            boardId: Long,
            flag: Int,
        ): Result<Int> =
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

        override suspend fun patchBoard(
            boardId: Long,
            patchBoardRequest: PatchBoardRequest,
        ): Result<PatchBoardResponse> =
            try {
                val response = boardApi.patchBoard(boardId, BoardMapper.toPatchBoardRequestDTO(patchBoardRequest))
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { BoardMapper.toPatchBoardResponse(it) } ?: throw NullPointerException("Null Response")
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
            gameStartDate: String?,
            maxPerson: Int?,
            preferredTeamId: Int?,
        ): Result<GetBoardListResponse> =
            try {
                val response = boardApi.getBoardList(gameStartDate, maxPerson, preferredTeamId)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body = response.body()?.let { BoardMapper.toGetBoardListResponse(it) } ?: throw java.lang.NullPointerException("Null Response")
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
