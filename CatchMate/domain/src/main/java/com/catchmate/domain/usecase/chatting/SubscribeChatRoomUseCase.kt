package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.repository.ChatWebSocketRepository
import io.reactivex.Observable
import javax.inject.Inject

class SubscribeChatRoomUseCase
    @Inject
    constructor(
        private val chatWebSocketRepository: ChatWebSocketRepository,
    ) {
        operator fun invoke(chatRoomId: Long): Observable<String> = chatWebSocketRepository.subscribe(chatRoomId)
    }
