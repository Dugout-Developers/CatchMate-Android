package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.datasource.remote.UserService
import com.catchmate.data.mapper.UserMapper
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

        override suspend fun getUserProfile(): GetUserProfileResponse? =
            try {
                val response = userApi.getUserProfile()
                if (response.isSuccessful) {
                    Log.d("UserProfileRepo", "통신 성공 : ${response.code()}")
                    response.body()?.let { UserMapper.toUserProfileResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Log.d("UserProfileRepo", "통신 실패 ${response.code()}\n$stringToJson")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
