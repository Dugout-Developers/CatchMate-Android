package com.catchmate.domain.repository

import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserAdditionalInfoResponse

interface SignUpRepository {
    suspend fun postUserAdditionalInfo(userAdditionalInfoRequest: UserAdditionalInfoRequest): UserAdditionalInfoResponse?
}
