package com.catchmate.domain.repository

import com.gmail.bishoybasily.stomp.lib.Event
import io.reactivex.Observable

interface ChatWebSocketRepository {
    fun connect(): Observable<Event>

//    fun disconnect()

    fun subscribe(chatRoomId: Long): Observable<String>

    fun sendMessage(
        chatRoomId: Long,
        message: String,
    ): Observable<Boolean>
}
