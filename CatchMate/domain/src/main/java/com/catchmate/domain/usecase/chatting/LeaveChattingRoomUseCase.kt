package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.repository.ChattingRepository
import javax.inject.Inject

class LeaveChattingRoomUseCase
    @Inject
    constructor(
        private val chattingRepository: ChattingRepository,
    ) {
        suspend operator fun invoke(roomId: Long): Result<Unit> = chattingRepository.deleteChattingRoom(roomId)
    }
