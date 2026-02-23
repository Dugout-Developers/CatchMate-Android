package com.catchmate.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingMessagesResponse
import com.catchmate.domain.model.chatting.PutChattingRoomAlarmResponse
import com.catchmate.domain.model.enumclass.ChatMessageType
import com.catchmate.domain.usecase.chatting.GetChattingCrewListUseCase
import com.catchmate.domain.usecase.chatting.GetChattingMessagesUseCase
import com.catchmate.domain.usecase.chatting.PutChattingRoomAlarmUseCase
import com.catchmate.presentation.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import javax.inject.Inject

@HiltViewModel
class ChattingRoomViewModel
    @Inject
    constructor(
        private val getChattingMessagesUseCase: GetChattingMessagesUseCase,
        private val getChattingCrewListUseCase: GetChattingCrewListUseCase,
        private val putChattingRoomAlarmUseCase: PutChattingRoomAlarmUseCase,
    ) : ViewModel() {
        var topic: Disposable? = null
        var stompClient: StompClient? = null
        private var okHttpClient: OkHttpClient? = null

        private val _getChattingMessagesResponse = MutableLiveData<List<GetChattingMessagesResponse>>()
        val getChattingMessagesResponse: LiveData<List<GetChattingMessagesResponse>>
            get() = _getChattingMessagesResponse

        private val _getChattingCrewListResponse = MutableLiveData<List<GetChattingCrewListResponse>>()
        val getChattingCrewListResponse: LiveData<List<GetChattingCrewListResponse>>
            get() = _getChattingCrewListResponse

        private val _chattingRoomInfo = MutableLiveData<ChatRoomInfo>()
        val chattingRoomInfo: LiveData<ChatRoomInfo>
            get() = _chattingRoomInfo

        private val _isLeft = MutableLiveData<Boolean>()
        val isLeft: LiveData<Boolean>
            get() = _isLeft

        private val _putChattingRoomAlarmResponse = MutableLiveData<PutChattingRoomAlarmResponse>()
        val putChattingRoomAlarmResponse: LiveData<PutChattingRoomAlarmResponse>
            get() = _putChattingRoomAlarmResponse

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        private val _isMessageSent = MutableLiveData<Boolean>()
        val isMessageSent: LiveData<Boolean>
            get() = _isMessageSent

        private val _isInstability = MutableLiveData<Boolean>()
        val isInstability: LiveData<Boolean>
            get() = _isInstability

        fun setChatRoomInfo(info: ChatRoomInfo) {
            _chattingRoomInfo.value = info
        }

        /** WebSocket 연결 */
        fun connectToWebSocket(
            chatRoomId: Long,
            accessToken: String,
        ) {
            viewModelScope.launch {
                okHttpClient =
                    OkHttpClient
                        .Builder()
                        .addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            },
                        ).build()

                val headerMap =
                    mapOf(
                        "Authorization" to accessToken,
                    )

                stompClient =
                    Stomp
                        .over(
                            Stomp.ConnectionProvider.OKHTTP,
                            BuildConfig.SERVER_SOCKET_URL,
                            headerMap,
                            okHttpClient,
                        ).apply {
                            withClientHeartbeat(25000)
                            withServerHeartbeat(35000)
                        }

                val stompHeaders =
                    listOf(
                        StompHeader("Authorization", accessToken)
                    )

                stompClient?.lifecycle()?.subscribe({ event ->
                    when (event.type) {
                        LifecycleEvent.Type.OPENED -> {
                            Log.d("Web Socket✅", "연결 성공")
                            handleWebSocketOpened(chatRoomId)
                        }

                        LifecycleEvent.Type.CLOSED -> {
                            Log.d("Web Socket💤", "연결 해제")
                        }

                        LifecycleEvent.Type.ERROR -> {
                            Log.i("Web Socket", "${event.exception}")
                            _isInstability.postValue(true)
                        }

                        else -> {}
                    }
                }, { error ->
                    Log.i("Web Socket", "${error.message}")
                    _isInstability.postValue(true)
                })

                stompClient?.connect(stompHeaders)
            }
        }

        private fun handleWebSocketOpened(
            chatRoomId: Long,
        ) {
            // 채팅방 구독
            topic =
                stompClient?.topic("/sub/chat/room/$chatRoomId")?.subscribe({ message ->
                    Log.i("✅ Msg", message.payload)
                    val jsonObject = JSONObject(message.payload)
                    val messageType = jsonObject.getString("messageType")
                    val chatMessage: GetChattingMessagesResponse =
                        when (messageType) {
//                            ChatMessageType.DATE.name -> {
//                                val roomId = jsonObject.getString("chatRoomId").toLong()
//                                val content = jsonObject.getString("content")
//                                val senderId = jsonObject.getString("senderId").toLong()
//                                ChatMessageInfo(
//                                    chatMessageId = "",
//                                    roomId = roomId,
//                                    content = content,
//                                    senderId = senderId,
//                                    messageType = messageType,
//                                )
//                            }

                            ChatMessageType.TEXT.name -> {
                                val messageId = jsonObject.getLong("messageId")
                                val roomId = jsonObject.getLong("roomId")
                                val senderId = jsonObject.getLong("senderId")
                                val senderNickname = jsonObject.getString("senderNickname")
                                val senderProfileImage = jsonObject.getString("senderProfileImage")
                                val content = jsonObject.getString("content")
                                val createdAt = jsonObject.getString("createdAt")
                                GetChattingMessagesResponse(
                                    messageId = messageId,
                                    chatRoomId = roomId,
                                    senderId = senderId,
                                    senderNickName = senderNickname,
                                    senderProfileImageUrl = senderProfileImage,
                                    content = content,
                                    messageType = messageType,
                                    createdAt = createdAt,
                                )
                            }

                            else -> { // 채팅방 나가고 들어올때 메시지 처리하기
                                GetChattingMessagesResponse(
                                    messageId = -1,
                                    chatRoomId = -1,
                                    senderId = -1,
                                    senderNickName = "",
                                    senderProfileImageUrl = "",
                                    content = "",
                                    messageType = "",
                                    createdAt = "",
                                )
                            }
                        }
                    addChatMessage(chatMessage)
                    sendIsMsgRead(chatRoomId)
                }, { error ->
                    Log.i("ws opened", "chatroom subscribe error / ${error.printStackTrace()}", error)
                })
        }

        private fun sendIsMsgRead(
            chatRoomId: Long,
        ) {
            viewModelScope.launch {
                val msg =
                    JSONObject()
                        .apply {
                            put("chatRoomId", chatRoomId)
                        }.toString()
                stompClient?.send("/pub/chat/read", msg)?.subscribe()
            }
        }

        fun sendMessage(
            request: String,
        ) {
            // 전달 성공 시 view의 edt 텍스트 비우기
            viewModelScope.launch {
                stompClient?.send("/pub/chat/message", request)?.subscribe({
                    Log.d("Web Socket📬", "메시지 전달")
                    _isMessageSent.value = true
                }, { error ->
                    Log.d("Web Socket✉️❌", "메시지 전송 실패", error)
                    _isMessageSent.value = false
                })
            }
        }

        fun leaveChattingRoom(request: String) {
            viewModelScope.launch {
                stompClient?.send("/pub/chat/leave", request)?.subscribe({
                    Log.d("Web Socket🚪", "방 탈퇴")
                    _isLeft.value = true
                }, { error ->
                    Log.d("Web Socket🚪❌", "방 탈퇴 실패", error)
                    _isLeft.value = false
                })
            }
        }

        fun sendEnterRequest(chatRoomId: Long) {
            viewModelScope.launch {
                val request =
                    JSONObject()
                        .apply {
                            put("chatRoomId", chatRoomId)
                        }.toString()
                stompClient?.send("/pub/chat/enter", request)?.subscribe({
                    Log.d("Web Socket🪟", "입장 알림 성공")
                }, { error ->
                    Log.d("Web Socket🪟❌", "입장 알림 실패", error)
                })
            }
        }

        override fun onCleared() {
            super.onCleared()
            topic?.dispose()
            stompClient?.disconnect()
        }

        private fun addChatMessage(chatMessage: GetChattingMessagesResponse) {
            val currentList = _getChattingMessagesResponse.value ?: emptyList<GetChattingMessagesResponse>()
            val updatedList = listOf(chatMessage) + currentList

            _getChattingMessagesResponse.postValue(updatedList)
        }

        fun getChattingMessages(
            chatRoomId: Long,
            lastMessageId: Long? = null,
            size: Int = 20,
            userId: Long,
        ) {
            viewModelScope.launch {
                val result = getChattingMessagesUseCase(chatRoomId, lastMessageId, size)
                result
                    .onSuccess { response ->
                        _getChattingMessagesResponse.value = response
                        val hasEnterMsg =
                            response.any {
                                it.messageType == "SYSTEM" && it.senderId == userId
                            }
                        if (response.isEmpty() || !hasEnterMsg) {
                            sendEnterRequest(chatRoomId)
                        }
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
//
//        fun putChattingRoomAlarm(
//            chatRoomId: Long,
//            enable: Boolean,
//        ) {
//            viewModelScope.launch {
//                val result = putChattingRoomAlarmUseCase(chatRoomId, enable)
//                result
//                    .onSuccess { response ->
//                        _putChattingRoomAlarmResponse.value = response
//                    }.onFailure { exception ->
//                        if (exception is ReissueFailureException) {
//                            _navigateToLogin.value = true
//                        } else {
//                            _errorMessage.value = exception.message
//                        }
//                    }
//            }
//        }
    }
