package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.EnrollService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.EnrollMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.EnrollCancelResponse
import com.catchmate.domain.model.PostEnrollRequest
import com.catchmate.domain.model.PostEnrollResponse
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

        override suspend fun postEnrollCancel(enrollId: Long): EnrollCancelResponse? =
            try {
                val response = enrollApi.postEnrollCancel(enrollId)
                if (response.isSuccessful) {
                    Log.d("EnrollRepo", "통신 성공")
                    response.body()?.let { EnrollMapper.toEnrollCancelResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Log.e("EnrollRepo", "통신 실패 : ${response.code()}\n$stringToJson")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
