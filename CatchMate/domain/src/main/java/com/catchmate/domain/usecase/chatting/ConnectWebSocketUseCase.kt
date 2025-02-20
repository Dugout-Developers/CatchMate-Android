package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.repository.ChatWebSocketRepository
import com.gmail.bishoybasily.stomp.lib.Event
import io.reactivex.Observable
import javax.inject.Inject

class ConnectWebSocketUseCase
    @Inject
    constructor(
        private val chatWebSocketRepository: ChatWebSocketRepository,
    ) {
        operator fun invoke(): Observable<Event> = chatWebSocketRepository.connect()
    }
