package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.BoardService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.BoardMapper
import com.catchmate.domain.exception.BookmarkFailureException
import com.catchmate.domain.exception.NonExistentTempBoardException
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.board.DeleteBoardLikeResponse
import com.catchmate.domain.model.board.DeleteBoardResponse
import com.catchmate.domain.model.board.GetBoardListResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.model.board.GetTempBoardResponse
import com.catchmate.domain.model.board.GetUserBoardListResponse
import com.catchmate.domain.model.board.PatchBoardLiftUpResponse
import com.catchmate.domain.model.board.PatchBoardRequest
import com.catchmate.domain.model.board.PatchBoardResponse
import com.catchmate.domain.model.board.PostBoardLikeResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
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
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toPostBoardResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
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

        override suspend fun postBoardLike(boardId: Long): Result<PostBoardLikeResponse> =
            try {
                val response = boardApi.postBoardLike(boardId)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toPostBoardLikeResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    if (response.code() == 400) {
                        Result.failure(BookmarkFailureException("$stringToJson"))
                    } else {
                        Result.failure(Exception("BoardRepo 통신 실패 : ${response.code()} - $stringToJson"))
                    }
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
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toPatchBoardResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
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

        override suspend fun patchBoardLiftUp(boardId: Long): Result<PatchBoardLiftUpResponse> =
            try {
                val response = boardApi.patchBoardLiftUp(boardId)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toPatchBoardLiftUpResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("$stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getBoardList(
            gameStartDate: String?,
            maxPerson: Int?,
            preferredTeamIdList: Array<Int>?
        ): Result<GetBoardListResponse> =
            try {
                val response = boardApi.getBoardList(gameStartDate, maxPerson, preferredTeamIdList)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toGetBoardListResponse(it)
                            }
                            ?: throw java.lang.NullPointerException("Null Response")
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

        override suspend fun getUserBoardList(userId: Long): Result<GetUserBoardListResponse> =
            try {
                val response = boardApi.getUserBoardList(userId)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toGetUserBoardListResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
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
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toGetBoardResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
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

        override suspend fun getLikedBoard(): Result<GetLikedBoardResponse> =
            try {
                val response = boardApi.getLikedBoard()
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toGetLikedBoardResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
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

        override suspend fun getTempBoard(): Result<GetTempBoardResponse> =
            try {
                val response = boardApi.getTempBoard()
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toGetTempBoardResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    if (response.code() == 404) {
                        Result.failure(NonExistentTempBoardException("$stringToJson"))
                    } else {
                        Result.failure(Exception("$stringToJson"))
                    }
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun deleteBoard(boardId: Long): Result<DeleteBoardResponse> =
            try {
                val response = boardApi.deleteBoard(boardId)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toDeleteBoardResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
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

        override suspend fun deleteBoardLike(boardId: Long): Result<DeleteBoardLikeResponse> =
            try {
                val response = boardApi.deleteBoardLike(boardId)
                if (response.isSuccessful) {
                    Log.d("BoardRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let {
                                BoardMapper.toDeleteBoardLikeResponse(it)
                            }
                            ?: throw NullPointerException("Null Response")
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
    }
