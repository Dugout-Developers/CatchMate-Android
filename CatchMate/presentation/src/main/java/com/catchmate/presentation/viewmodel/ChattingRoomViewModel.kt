package com.catchmate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.catchmate.domain.usecase.chatting.ConnectWebSocketUseCase
import com.catchmate.domain.usecase.chatting.SendChatUseCase
import com.catchmate.domain.usecase.chatting.SubscribeChatRoomUseCase
import com.gmail.bishoybasily.stomp.lib.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ChattingRoomViewModel
    @Inject
    constructor(
        private val connectWebSocketUseCase: ConnectWebSocketUseCase,
        private val subscribeChatRoomUseCase: SubscribeChatRoomUseCase,
        private val sendChatUseCase: SendChatUseCase,
    ) : ViewModel() {
        val disposables = CompositeDisposable()

        fun connectToWebSocket(): Observable<Event> =
            connectWebSocketUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSubscribe { disposables.add(it) }

        fun subscribeToChatRoom(chatRoomId: Long): Observable<String> =
            subscribeChatRoomUseCase(chatRoomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposables.add(it) }

        fun sendChat(chatRoomId: Long, message: String): Observable<Boolean> = sendChatUseCase(chatRoomId, message)

        override fun onCleared() {
            super.onCleared()
            disposables.clear()
        }
    }
