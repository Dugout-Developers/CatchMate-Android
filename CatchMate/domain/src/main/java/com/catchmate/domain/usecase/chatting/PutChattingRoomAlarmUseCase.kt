package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.model.chatting.PutChattingRoomAlarmRequest
import com.catchmate.domain.repository.ChattingRepository
import javax.inject.Inject

class PutChattingRoomAlarmUseCase
    @Inject
    constructor(
        private val chattingRepository: ChattingRepository,
    ) {
        suspend operator fun invoke(
            roomId: Long,
            request: PutChattingRoomAlarmRequest,
        ): Result<Unit> = chattingRepository.putChattingRoomAlarm(roomId, request)
    }
