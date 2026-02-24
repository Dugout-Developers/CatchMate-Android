package com.catchmate.domain.repository

import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingMessagesResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.model.chatting.PutChattingRoomAlarmResponse
import okhttp3.MultipartBody

interface ChattingRepository {
    suspend fun getChattingRoomList(
        page: Int,
        size: Int,
    ): Result<GetChattingRoomListResponse>

    suspend fun getChattingCrewList(chatRoomId: Long): Result<List<GetChattingCrewListResponse>>

    suspend fun patchChattingRoomImage(
        roomId: Long,
        chatRoomImage: MultipartBody.Part,
    ): Result<Unit>

    suspend fun putChattingRoomAlarm(
        chatRoomId: Long,
        enable: Boolean,
    ): Result<PutChattingRoomAlarmResponse>

    suspend fun deleteChattingRoom(roomId: Long): Result<Unit>

    suspend fun deleteChattingCrew(
        chatRoomId: Long,
        targetUserId: Long,
    ): Result<Unit>

    suspend fun getChattingMessages(
        chatRoomId: Long,
        lastMessageId: Long?,
        size: Int,
    ): Result<List<GetChattingMessagesResponse>>
}
