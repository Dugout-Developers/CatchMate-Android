package com.catchmate.domain.repository

import com.catchmate.domain.model.enumclass.AlarmType
import com.catchmate.domain.model.user.DeleteBlockedUserResponse
import com.catchmate.domain.model.user.GetBlockedUserListResponse
import com.catchmate.domain.model.user.GetUserProfileByIdResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.domain.model.user.PatchUserAlarmResponse
import com.catchmate.domain.model.user.PatchUserProfileResponse
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.user.PostUserAdditionalInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRepository {
    suspend fun getUserProfile(): Result<GetUserProfileResponse>

    suspend fun getUserProfileById(profileUserId: Long): Result<GetUserProfileByIdResponse>

    suspend fun getBlockedUserList(): Result<GetBlockedUserListResponse>

    suspend fun postUserAdditionalInfo(postUserAdditionalInfoRequest: PostUserAdditionalInfoRequest): Result<PostUserAdditionalInfoResponse>

    suspend fun patchUserProfile(
        request: RequestBody,
        profileImage: MultipartBody.Part,
    ): Result<PatchUserProfileResponse>

    suspend fun patchUserAlarm(
        alarmType: AlarmType,
        isEnabled: String,
    ): Result<PatchUserAlarmResponse>

    suspend fun deleteBlockedUser(blockedUserId: Long): Result<DeleteBlockedUserResponse>
}
