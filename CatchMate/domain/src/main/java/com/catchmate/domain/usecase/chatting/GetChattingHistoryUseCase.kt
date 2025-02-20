package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.repository.ChattingRepository
import javax.inject.Inject

class GetChattingHistoryUseCase
    @Inject
    constructor(
        private val chattingRepository: ChattingRepository,
    ) {
        suspend operator fun invoke(
            chatRoomId: Long,
            page: Int,
            size: Int?,
        ): Result<GetChattingHistoryResponse> = chattingRepository.getChattingHistory(chatRoomId, page, size)
    }
