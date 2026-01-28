package com.catchmate.domain.usecase.user

import com.catchmate.domain.model.user.GetCheckNicknameResponse
import com.catchmate.domain.repository.UserRepository
import javax.inject.Inject

class GetCheckNicknameUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        suspend operator fun invoke(nickName: String): Result<GetCheckNicknameResponse> = userRepository.getCheckNickname(nickName)
    }
