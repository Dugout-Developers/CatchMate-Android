package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.chatting.ChatMessageInfo
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.DeleteChattingRoomResponse
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.usecase.chatting.ConnectWebSocketUseCase
import com.catchmate.domain.usecase.chatting.LeaveChattingRoomUseCase
import com.catchmate.domain.usecase.chatting.GetChattingCrewListUseCase
import com.catchmate.domain.usecase.chatting.GetChattingHistoryUseCase
import com.catchmate.domain.usecase.chatting.GetChattingRoomInfoUseCase
import com.catchmate.domain.usecase.chatting.SendChatUseCase
import com.catchmate.domain.usecase.chatting.SubscribeChatRoomUseCase
import com.gmail.bishoybasily.stomp.lib.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ChattingRoomViewModel
    @Inject
    constructor(
        private val connectWebSocketUseCase: ConnectWebSocketUseCase,
        private val subscribeChatRoomUseCase: SubscribeChatRoomUseCase,
        private val sendChatUseCase: SendChatUseCase,
        private val getChattingHistoryUseCase: GetChattingHistoryUseCase,
        private val getChattingCrewListUseCase: GetChattingCrewListUseCase,
        private val getChattingRoomInfoUseCase: GetChattingRoomInfoUseCase,
        private val deleteChattingRoomUseCase: LeaveChattingRoomUseCase,
    ) : ViewModel() {
        val disposables = CompositeDisposable()

        private val _getChattingHistoryResponse = MutableLiveData<GetChattingHistoryResponse>()
        val getChattingHistoryResponse: LiveData<GetChattingHistoryResponse>
            get() = _getChattingHistoryResponse

        private val _getChattingCrewListResponse = MutableLiveData<GetChattingCrewListResponse>()
        val getChattingCrewListResponse: LiveData<GetChattingCrewListResponse>
            get() = _getChattingCrewListResponse

        private val _chattingRoomInfo = MutableLiveData<ChatRoomInfo>()
        val chattingRoomInfo: LiveData<ChatRoomInfo>
            get() = _chattingRoomInfo

        private val _deleteChattingRoomResponse = MutableLiveData<DeleteChattingRoomResponse>()
        val deleteChattingRoomResponse: LiveData<DeleteChattingRoomResponse>
            get() = _deleteChattingRoomResponse

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

        fun sendChat(
            chatRoomId: Long,
            message: String,
        ): Observable<Boolean> = sendChatUseCase(chatRoomId, message)

        override fun onCleared() {
            super.onCleared()
            disposables.clear()
        }

        fun addChatMessage(chatMessageInfo: ChatMessageInfo) {
            val currentList = _getChattingHistoryResponse.value?.chatMessageInfoList ?: emptyList()
            val updatedList = listOf(chatMessageInfo) + currentList

            val updatedResponse =
                _getChattingHistoryResponse.value?.copy(
                    chatMessageInfoList = updatedList,
                ) ?: GetChattingHistoryResponse(
                    chatMessageInfoList = updatedList,
                    totalPages = 1,
                    totalElements = 1,
                    isFirst = true,
                    isLast = true,
                )

            _getChattingHistoryResponse.postValue(updatedResponse)
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

        fun getChattingRoomInfo(chatRoomId: Long) {
            viewModelScope.launch {
                val result = getChattingRoomInfoUseCase(chatRoomId)
                result
                    .onSuccess { info ->
                        _chattingRoomInfo.value = info
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }

        fun deleteChattingRoom(chatRoomId: Long) {
            viewModelScope.launch {
                val result = deleteChattingRoomUseCase(chatRoomId)
                result
                    .onSuccess { response ->
                        _deleteChattingRoomResponse.value = response
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
