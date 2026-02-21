package com.catchmate.data.repository

import com.catchmate.data.datasource.remote.ChattingService
import com.catchmate.data.datasource.remote.RetrofitClient
import com.catchmate.data.mapper.ChattingMapper
import com.catchmate.data.util.ApiResponseHandleUtil.apiCall
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.DeleteChattingCrewKickOutResponse
import com.catchmate.domain.model.chatting.DeleteChattingRoomResponse
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingMessagesResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.model.chatting.PatchChattingRoomImageResponse
import com.catchmate.domain.model.chatting.PutChattingRoomAlarmResponse
import com.catchmate.domain.repository.ChattingRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ChattingRepositoryImpl
    @Inject
    constructor(
        retrofitClient: RetrofitClient,
    ) : ChattingRepository {
        private val chattingApi = retrofitClient.createApi<ChattingService>()
        private val tag = "ChattingRepo"

        override suspend fun getChattingRoomList(
            page: Int,
            size: Int,
        ): Result<GetChattingRoomListResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.getChattingRoomList(page, size) },
                transform = { ChattingMapper.toGetChattingRoomListResponse(it!!) },
            )

        override suspend fun getChattingCrewList(chatRoomId: Long): Result<List<GetChattingCrewListResponse>> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.getChattingCrewList(chatRoomId) },
                transform = { list ->
                    list?.map { crew ->
                        ChattingMapper.toGetChattingCrewListResponse(crew)
                    } ?: emptyList()
                },
            )

        override suspend fun getChattingRoomInfo(chatRoomId: Long): Result<ChatRoomInfo> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.getChattingRoomInfo(chatRoomId) },
                transform = { ChattingMapper.toChatRoomInfo(it!!) },
            )

        override suspend fun patchChattingRoomImage(
            chatRoomId: Long,
            chatRoomImage: MultipartBody.Part,
        ): Result<PatchChattingRoomImageResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.patchChattingRoomImage(chatRoomId, chatRoomImage) },
                transform = { ChattingMapper.toPatchChattingRoomImageResponse(it!!) },
            )

        override suspend fun putChattingRoomAlarm(
            chatRoomId: Long,
            enable: Boolean,
        ): Result<PutChattingRoomAlarmResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.putChattingRoomAlarm(chatRoomId, enable) },
                transform = { ChattingMapper.toPutChattingRoomAlarmResponse(it!!) },
            )

        override suspend fun deleteChattingRoom(chatRoomId: Long): Result<DeleteChattingRoomResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.deleteChattingRoom(chatRoomId) },
                transform = { ChattingMapper.toDeleteChattingRoomResponse(it!!) },
            )

        override suspend fun deleteChattingCrewKickOut(
            chatRoomId: Long,
            userId: Long,
        ): Result<DeleteChattingCrewKickOutResponse> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.deleteChattingCrewKickOut(chatRoomId, userId) },
                transform = { ChattingMapper.toDeleteChattingCrewKickOutResponse(it!!) },
            )

        override suspend fun getChattingMessages(
            chatRoomId: Long,
            lastMessageId: Long?,
            size: Int,
        ): Result<List<GetChattingMessagesResponse>> =
            apiCall(
                tag = this.tag,
                apiFunction = { chattingApi.getChattingMessages(chatRoomId, lastMessageId, size) },
                transform = { list ->
                    list?.map { message ->
                        ChattingMapper.toGetChattingHistoryResponse(message)
                    } ?: emptyList()
                },
            )
    }
