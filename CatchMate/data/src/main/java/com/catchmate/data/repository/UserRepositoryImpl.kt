package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.datasource.remote.UserService
import com.catchmate.data.mapper.UserMapper
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.model.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.PostUserAdditionalInfoResponse
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
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                UserMapper.toGetUserProfileResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("UserRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun postUserAdditionalInfo(
            postUserAdditionalInfoRequest: PostUserAdditionalInfoRequest,
        ): Result<PostUserAdditionalInfoResponse> =
            try {
                val response = userApi.postUserAdditionalInfo(UserMapper.toPostUserAdditionalInfoRequestDTO(postUserAdditionalInfoRequest))
                if (response.isSuccessful) {
                    Log.d("User - post add info", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                UserMapper.toPostUserAdditionalInfoResponse(responseBody)
                            }
                            ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Result.failure(Exception("UserRepo 통신 실패 : ${response.code()} - $stringToJson"))
                }
            } catch (e: ReissueFailureException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
