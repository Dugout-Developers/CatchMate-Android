package com.catchmate.domain.usecase.user

import com.catchmate.domain.model.user.GetUserAlarmResponse
import com.catchmate.domain.repository.UserRepository
import javax.inject.Inject

class GetUserAlarmResponseUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        suspend operator fun invoke(): Result<GetUserAlarmResponse> = userRepository.getUserAlarmState()
    }
