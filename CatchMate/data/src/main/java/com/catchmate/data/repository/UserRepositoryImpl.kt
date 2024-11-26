package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.datasource.remote.UserService
import com.catchmate.data.mapper.UserMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.repository.UserRepository
import org.json.JSONObject
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : UserRepository {
        private val userApi = retrofitClient.createApi<UserService>()

        override suspend fun getUserProfile(): Result<GetUserProfileResponse> =
            try {
                val response = userApi.getUserProfile()
                if (response.isSuccessful) {
                    Log.d("UserProfileRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { UserMapper.toGetUserProfileResponse(it) } ?: throw NullPointerException("Null Response")
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
    }
