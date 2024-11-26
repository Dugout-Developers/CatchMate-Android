package com.catchmate.domain.repository

import com.catchmate.domain.model.GetUserProfileResponse

interface UserRepository {
    suspend fun getUserProfile(): Result<GetUserProfileResponse>
}
