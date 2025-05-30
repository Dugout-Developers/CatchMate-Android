package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.datasource.remote.UserService
import com.catchmate.data.mapper.UserMapper
import com.catchmate.data.mapper.UserMapper.toDeleteBlockedUserResponse
import com.catchmate.data.mapper.UserMapper.toDeleteUserAccountResponse
import com.catchmate.data.mapper.UserMapper.toGetBlockedUserListResponse
import com.catchmate.data.mapper.UserMapper.toGetUnreadInfoResponse
import com.catchmate.data.mapper.UserMapper.toPostUserBlockResponse
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.exception.UserBlockFailureException
import com.catchmate.domain.model.user.DeleteBlockedUserResponse
import com.catchmate.domain.model.user.DeleteUserAccountResponse
import com.catchmate.domain.model.user.GetBlockedUserListResponse
import com.catchmate.domain.model.user.GetUnreadInfoResponse
import com.catchmate.domain.model.user.GetUserProfileByIdResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.domain.model.user.PatchUserAlarmResponse
import com.catchmate.domain.model.user.PatchUserProfileResponse
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.user.PostUserAdditionalInfoResponse
import com.catchmate.domain.model.user.PostUserBlockResponse
import com.catchmate.domain.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

        override suspend fun getUserProfileById(profileUserId: Long): Result<GetUserProfileByIdResponse> =
            try {
                val response = userApi.getUserProfileById(profileUserId)
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                UserMapper.toGetUserProfileByIdResponse(responseBody)
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

        override suspend fun getBlockedUserList(page: Int): Result<GetBlockedUserListResponse> =
            try {
                val response = userApi.getBlockedUserList(page)
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { toGetBlockedUserListResponse(it) } ?: throw NullPointerException("Null Response")
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

        override suspend fun getUnreadInfo(): Result<GetUnreadInfoResponse> =
            try {
                val response = userApi.getUnreadInfo()
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { toGetUnreadInfoResponse(it) } ?: throw NullPointerException("Null Response")
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

        override suspend fun postUserBlock(blockedUserId: Long): Result<PostUserBlockResponse> =
            try {
                val response = userApi.postUserBlock(blockedUserId)
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { toPostUserBlockResponse(it) } ?: throw NullPointerException("Null Response")
                    Result.success(body)
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    if (response.code() == 400) {
                        val message = stringToJson.getString("message")
                        Result.failure(UserBlockFailureException(message))
                    } else {
                        Result.failure(Exception("UserRepo 통신 실패 : $stringToJson"))
                    }
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

        override suspend fun patchUserProfile(
            request: RequestBody,
            profileImage: MultipartBody.Part,
        ): Result<PatchUserProfileResponse> =
            try {
                val response = userApi.patchUserProfile(request, profileImage)
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                UserMapper.toPatchUserProfileResponse(responseBody)
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

        override suspend fun patchUserAlarm(
            alarmType: String,
            isEnabled: String,
        ): Result<PatchUserAlarmResponse> =
            try {
                val response = userApi.patchUserAlarm(alarmType, isEnabled)
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body =
                        response
                            .body()
                            ?.let { responseBody ->
                                UserMapper.toPatchUserAlarmResponse(responseBody)
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

        override suspend fun deleteBlockedUser(blockedUserId: Long): Result<DeleteBlockedUserResponse> =
            try {
                val response = userApi.deleteBlockedUser(blockedUserId)
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { toDeleteBlockedUserResponse(it) } ?: throw NullPointerException("Null Response")
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

        override suspend fun deleteUserAccount(): Result<DeleteUserAccountResponse> =
            try {
                val response = userApi.deleteUserAccount()
                if (response.isSuccessful) {
                    Log.d("UserRepo", "통신 성공 : ${response.code()}")
                    val body = response.body()?.let { toDeleteUserAccountResponse(it) } ?: throw NullPointerException("Null Response")
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
