package com.catchmate.data.repository

import com.catchmate.data.datasource.remote.ChatWebSocketClient
import com.catchmate.domain.repository.ChatWebSocketRepository
import com.gmail.bishoybasily.stomp.lib.Event
import io.reactivex.Observable
import javax.inject.Inject

class ChatWebSocketRepositoryImpl
    @Inject
    constructor(
        chatWebSocketClient: ChatWebSocketClient,
    ) : ChatWebSocketRepository {
        private val stompClient = chatWebSocketClient.stompClient

        override fun connect(): Observable<Event> = stompClient.connect()

        override fun subscribe(chatRoomId: Long): Observable<String> = stompClient.join("/topic/chat.$chatRoomId")

        override fun sendMessage(
            chatRoomId: Long,
            message: String,
        ): Observable<Boolean> = stompClient.send("/app/chat.$chatRoomId", message)
    }
