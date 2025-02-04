package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.EnrollService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.EnrollMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.enroll.DeleteEnrollResponse
import com.catchmate.domain.model.enroll.GetAllReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetEnrollNewCountResponse
import com.catchmate.domain.model.enroll.GetReceivedEnrollResponse
import com.catchmate.domain.model.enroll.GetRequestedEnrollListResponse
import com.catchmate.domain.model.enroll.PatchEnrollAcceptResponse
import com.catchmate.domain.model.enroll.PatchEnrollRejectResponse
import com.catchmate.domain.model.enroll.PostEnrollRequest
import com.catchmate.domain.model.enroll.PostEnrollResponse
import com.catchmate.domain.repository.EnrollRepository
import org.json.JSONObject
import javax.inject.Inject

class EnrollRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : EnrollRepository {
        private val enrollApi = retrofitClient.createApi<EnrollService>()

        override suspend fun postEnroll(
            boardId: Long,
            postEnrollRequest: PostEnrollRequest,
        ): Result<PostEnrollResponse> =
            try {
                val response = enrollApi.postEnroll(boardId, EnrollMapper.toPostEnrollRequestDTO(postEnrollRequest))
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공")
                    val body = response.body()?.let { EnrollMapper.toPostEnrollResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun patchEnrollReject(enrollId: Long): Result<PatchEnrollRejectResponse> =
            try {
                val response = enrollApi.patchEnrollReject(enrollId)
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                EnrollMapper.toPatchEnrollRejectResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun patchEnrollAccept(enrollId: Long): Result<PatchEnrollAcceptResponse> =
            try {
                val response = enrollApi.patchEnrollAccept(enrollId)
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                EnrollMapper.toPatchEnrollAcceptResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getRequestedEnrollList(page: Int): Result<GetRequestedEnrollListResponse> =
            try {
                val response = enrollApi.getRequestedEnrollList(page)
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                EnrollMapper.toGetRequestedEnrollListResponse(responseBody)
                            }
                            ?: throw java.lang.NullPointerException("Null Exception")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getReceivedEnroll(boardId: Long): Result<GetReceivedEnrollResponse> =
            try {
                val response = enrollApi.getReceivedEnroll(boardId)
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                EnrollMapper.toGetReceivedEnrollResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getAllReceivedEnroll(): Result<GetAllReceivedEnrollResponse> =
            try {
                val response = enrollApi.getAllReceivedEnroll()
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                EnrollMapper.toGetAllReceivedEnrollResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getEnrollNewCount(): Result<GetEnrollNewCountResponse> =
            try {
                val response = enrollApi.getEnrollNewCount()
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                EnrollMapper.toGetEnrollNewCountResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun deleteEnroll(enrollId: Long): Result<DeleteEnrollResponse> =
            try {
                val response = enrollApi.deleteEnroll(enrollId)
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                EnrollMapper.toDeleteEnrollResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("EnrollRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
