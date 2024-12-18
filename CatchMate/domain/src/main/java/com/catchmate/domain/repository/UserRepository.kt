package com.catchmate.domain.repository

import com.catchmate.domain.model.GetUserProfileByIdResponse
import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.model.PatchUserAlarmResponse
import com.catchmate.domain.model.PatchUserProfileRequest
import com.catchmate.domain.model.PatchUserProfileResponse
import com.catchmate.domain.model.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.PostUserAdditionalInfoResponse

interface UserRepository {
    suspend fun getUserProfile(): Result<GetUserProfileResponse>

    suspend fun getUserProfileById(profileUserId: Long): Result<GetUserProfileByIdResponse>

    suspend fun postUserAdditionalInfo(postUserAdditionalInfoRequest: PostUserAdditionalInfoRequest): Result<PostUserAdditionalInfoResponse>

    suspend fun patchUserProfile(patchUserProfileRequest: PatchUserProfileRequest): Result<PatchUserProfileResponse>

    suspend fun patchUserAlarm(pushAgreement: String): Result<PatchUserAlarmResponse>
}
