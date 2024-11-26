package com.catchmate.domain.repository

import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.model.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.PostUserAdditionalInfoResponse

interface UserRepository {
    suspend fun getUserProfile(): Result<GetUserProfileResponse>

    suspend fun postUserAdditionalInfo(postUserAdditionalInfoRequest: PostUserAdditionalInfoRequest): Result<PostUserAdditionalInfoResponse>
}
