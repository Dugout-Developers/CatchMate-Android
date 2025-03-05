package com.catchmate.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.chatting.ChatMessageId
import com.catchmate.domain.model.chatting.ChatMessageInfo
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.DeleteChattingRoomResponse
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.usecase.chatting.GetChattingCrewListUseCase
import com.catchmate.domain.usecase.chatting.GetChattingHistoryUseCase
import com.catchmate.domain.usecase.chatting.GetChattingRoomInfoUseCase
import com.catchmate.domain.usecase.chatting.LeaveChattingRoomUseCase
import com.catchmate.presentation.BuildConfig
import com.catchmate.presentation.util.DateUtils.getCurrentTimeFormatted
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChattingRoomViewModel
    @Inject
    constructor(
        private val getChattingHistoryUseCase: GetChattingHistoryUseCase,
        private val getChattingCrewListUseCase: GetChattingCrewListUseCase,
        private val getChattingRoomInfoUseCase: GetChattingRoomInfoUseCase,
        private val deleteChattingRoomUseCase: LeaveChattingRoomUseCase,
    ) : ViewModel() {
        private val maxRetry = 5
        private val intervalMillis = 1000L
        lateinit var stompConnection: Disposable
        lateinit var topic: Disposable

        private val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    },
                ).build()

        private val stompClient =
            StompClient(okHttpClient, intervalMillis).apply {
                url = BuildConfig.SERVER_SOCKET_URL
            }

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

        private val _isMessageSent = MutableLiveData<Boolean>()
        val isMessageSent: LiveData<Boolean>
            get() = _isMessageSent

        /** WebSocket 연결 */
        fun connectToWebSocket(chatRoomId: Long) {
            var retryCount = 0
            viewModelScope.launch {
                stompConnection =
                    stompClient
                        .connect()
                        .retryWhen { error ->
                            error
                                .takeWhile { retryCount < maxRetry }
                                .doOnNext {
                                    retryCount++
                                    Log.e("Web Socket🔄", "retry : $retryCount / 5")
                                }
                        }.subscribe { event ->
                            when (event.type) {
                                Event.Type.OPENED -> {
                                    Log.d("Web Socket✅", "연결 성공")
                                    handleWebSocketOpened(chatRoomId)
                                }
                                Event.Type.CLOSED -> {
                                    Log.d("Web Socket💤", "연결 해제")
                                }
                                Event.Type.ERROR -> {
                                    Log.e("Web Socket", "${event.exception}")
                                    if (retryCount >= maxRetry) {
                                        Log.e("Web Socket🚫", "최대 재시도 횟수 초과")
                                    }
                                }
                                else -> {}
                            }
                        }
            }
        }

        private fun handleWebSocketOpened(chatRoomId: Long) {
            topic =
                stompClient.join("/topic/chat.$chatRoomId").subscribe { message ->
                    Log.d("✅ Msg", message)
                    val jsonObject = JSONObject(message)
                    val messageType = jsonObject.getString("messageType")
                    val senderId = jsonObject.getString("senderId").toLong()
                    val content = jsonObject.getString("content")
                    val chatMessageId = ChatMessageId(date = getCurrentTimeFormatted())
                    val chatMessageInfo =
                        ChatMessageInfo(
                            id = chatMessageId,
                            content = content,
                            senderId = senderId,
                            messageType = messageType,
                        )
                    Log.e("⭐️JSON 확인", "$messageType - $senderId - $content - ${chatMessageId.date}")
                    addChatMessage(chatMessageInfo)
                }
        }

        fun sendMessage(
            chatRoomId: Long,
            message: String,
        ) {
            // 전달 성공 시 view의 edt 텍스트 비우기
            viewModelScope.launch {
                stompClient.send("/app/chat.$chatRoomId", message).subscribe({ isSend ->
                    if (isSend) {
                        Log.d("Web Socket📬", "메시지 전달")
                        _isMessageSent.value = true
                    } else {
                        Log.e("Web Socket😩", "메시지 전달 실패")
                        _isMessageSent.value = false
                    }
                }, { error ->
                    Log.e("Web Socket✉️❌", "메시지 전송 실패", error)
                    _isMessageSent.value = false
                })
            }
        }

        override fun onCleared() {
            super.onCleared()
            topic.dispose()
            stompConnection.dispose()
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
            size: Int? = 30,
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
