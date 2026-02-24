package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.repository.ChattingRepository
import javax.inject.Inject

class KickOutChattingCrewUseCase
    @Inject
    constructor(
        private val chattingRepository: ChattingRepository,
    ) {
        suspend operator fun invoke(
            chatRoomId: Long,
            targetUserId: Long,
        ): Result<Unit> = chattingRepository.deleteChattingCrew(chatRoomId, targetUserId)
    }
