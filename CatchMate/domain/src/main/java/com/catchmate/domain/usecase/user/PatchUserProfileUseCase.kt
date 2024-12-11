package com.catchmate.domain.usecase.user

import com.catchmate.domain.model.PatchUserProfileRequest
import com.catchmate.domain.model.PatchUserProfileResponse
import com.catchmate.domain.repository.UserRepository
import javax.inject.Inject

class PatchUserProfileUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        suspend fun patchUserProfile(
            request: PatchUserProfileRequest,
        ): Result<PatchUserProfileResponse> = userRepository.patchUserProfile(request)
    }
