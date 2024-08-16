package com.catchmate.domain.repository

import com.catchmate.domain.model.CheckNicknameResponse
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserResponse

interface SignUpRepository {
    suspend fun getNicknameAvailability(
        accessToken: String,
        nickName: String,
    ): CheckNicknameResponse?

    suspend fun patchUserAdditionalInfo(
        accessToken: String,
        userAdditionalInfoRequest: UserAdditionalInfoRequest,
    ): UserResponse?
}
