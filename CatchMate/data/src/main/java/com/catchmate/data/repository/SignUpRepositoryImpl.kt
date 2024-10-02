package com.catchmate.data.repository

import android.util.Log
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.datasource.remote.SignUpService
import com.catchmate.data.mapper.SignUpMapper
import com.catchmate.domain.model.CheckNicknameResponse
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserAdditionalInfoResponse
import com.catchmate.domain.repository.SignUpRepository
import org.json.JSONObject
import javax.inject.Inject

class SignUpRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : SignUpRepository {
        private val signUpApi = retrofitClient.createApi<SignUpService>()

        override suspend fun getNicknameAvailability(nickName: String): CheckNicknameResponse? =
            try {
                val response = signUpApi.getNicknameAvailability(nickName)
                if (response.isSuccessful) {
                    Log.d("AuthRepo", "통신 성공 : ${response.code()}")
                    response.body()?.let { SignUpMapper.toCheckNicknameResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    Log.d("AuthRepo", "통신 실패 : ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        override suspend fun postUserAdditionalInfo(userAdditionalInfoRequest: UserAdditionalInfoRequest): UserAdditionalInfoResponse? =
            try {
                val response =
                    signUpApi
                        .postUserAdditionalInfo(
                            SignUpMapper
                                .toUserAdditionalInfoRequestDTO(userAdditionalInfoRequest),
                        )
                if (response.isSuccessful) {
                    Log.d("SignUpRepo", "통신 성공 ${response.code()}")
                    response.body()?.let { SignUpMapper.toUserAdditionalInfoResponse(it) } ?: throw Exception("Empty Response")
                } else {
                    val stringToJson = JSONObject(response.errorBody()?.string()!!)
                    Log.d("SignUpRepo", "통신 실패 ${response.code()}\n$stringToJson")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
    }
