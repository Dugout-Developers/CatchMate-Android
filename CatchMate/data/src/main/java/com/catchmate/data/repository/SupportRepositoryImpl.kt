package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.datasource.remote.SupportService
import com.catchmate.data.mapper.SupportMapper.toPostInquiryRequestDTO
import com.catchmate.data.mapper.SupportMapper.toPostInquiryResponse
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.support.PostInquiryRequest
import com.catchmate.domain.model.support.PostInquiryResponse
import com.catchmate.domain.repository.SupportRepository
import org.json.JSONObject
import javax.inject.Inject

class SupportRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : SupportRepository {
        private val supportApi = retrofitClient.createApi<SupportService>()
        override suspend fun postInquiry(request: PostInquiryRequest): Result<PostInquiryResponse> =
            try {
                val response = supportApi.postInquiry(toPostInquiryRequestDTO(request))
                if (response.isSuccessful) {
                    Log.d("Support Repo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { toPostInquiryResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("NotiRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
