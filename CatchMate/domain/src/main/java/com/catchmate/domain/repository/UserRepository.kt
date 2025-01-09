package com.catchmate.domain.repository

import com.catchmate.domain.model.enumclass.AlarmType
import com.catchmate.domain.model.user.GetUserProfileByIdResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.domain.model.user.PatchUserAlarmResponse
import com.catchmate.domain.model.user.PatchUserProfileRequest
import com.catchmate.domain.model.user.PatchUserProfileResponse
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.user.PostUserAdditionalInfoResponse

interface UserRepository {
    suspend fun getUserProfile(): Result<GetUserProfileResponse>

    suspend fun getUserProfileById(profileUserId: Long): Result<GetUserProfileByIdResponse>

    suspend fun postUserAdditionalInfo(postUserAdditionalInfoRequest: PostUserAdditionalInfoRequest): Result<PostUserAdditionalInfoResponse>

    suspend fun patchUserProfile(patchUserProfileRequest: PatchUserProfileRequest): Result<PatchUserProfileResponse>

    suspend fun patchUserAlarm(
        alarmType: AlarmType,
        isEnabled: String,
    ): Result<PatchUserAlarmResponse>
}
