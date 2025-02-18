package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.usecase.chatting.ConnectWebSocketUseCase
import com.catchmate.domain.usecase.chatting.GetChattingCrewListUseCase
import com.catchmate.domain.usecase.chatting.GetChattingHistoryUseCase
import com.catchmate.domain.usecase.chatting.SendChatUseCase
import com.catchmate.domain.usecase.chatting.SubscribeChatRoomUseCase
import com.gmail.bishoybasily.stomp.lib.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingRoomViewModel
    @Inject
    constructor(
        private val connectWebSocketUseCase: ConnectWebSocketUseCase,
        private val subscribeChatRoomUseCase: SubscribeChatRoomUseCase,
        private val sendChatUseCase: SendChatUseCase,
        private val getChattingHistoryUseCase: GetChattingHistoryUseCase,
        private val getChattingCrewListUseCase: GetChattingCrewListUseCase,
    ) : ViewModel() {
        val disposables = CompositeDisposable()

        private val _getChattingHistoryResponse = MutableLiveData<GetChattingHistoryResponse>()
        val getChattingHistoryResponse: LiveData<GetChattingHistoryResponse>
            get() = _getChattingHistoryResponse

        private val _getChattingCrewListResponse = MutableLiveData<GetChattingCrewListResponse>()
        val getChattingCrewListResponse: LiveData<GetChattingCrewListResponse>
            get() = _getChattingCrewListResponse

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

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

        fun getChattingHistory(
            chatRoomId: Long,
            page: Int,
            size: Int? = null,
        ) {
            viewModelScope.launch {
                val result = getChattingHistoryUseCase(chatRoomId, page, size)
                result
                    .onSuccess { response ->
                        _getChattingHistoryResponse.value = response
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }

        fun getChattingCrewList(chatRoomId: Long) {
            viewModelScope.launch {
                val result = getChattingCrewListUseCase(chatRoomId)
                result
                    .onSuccess { response ->
                        _getChattingCrewListResponse.value = response
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }
    }
