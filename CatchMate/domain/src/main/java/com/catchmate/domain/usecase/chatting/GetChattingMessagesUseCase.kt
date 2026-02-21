package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.model.chatting.GetChattingMessagesResponse
import com.catchmate.domain.repository.ChattingRepository
import javax.inject.Inject

class GetChattingMessagesUseCase
    @Inject
    constructor(
        private val chattingRepository: ChattingRepository,
    ) {
        suspend operator fun invoke(
            chatRoomId: Long,
            lastMessageId: Long?,
            size: Int,
        ): Result<List<GetChattingMessagesResponse>> = chattingRepository.getChattingMessages(chatRoomId, lastMessageId, size)
    }
