package com.catchmate.domain.repository

import com.catchmate.domain.model.chatting.DeleteChattingCrewKickOutResponse
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingMessagesResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.model.chatting.PatchChattingRoomImageResponse
import com.catchmate.domain.model.chatting.PutChattingRoomAlarmResponse
import okhttp3.MultipartBody

interface ChattingRepository {
    suspend fun getChattingRoomList(
        page: Int,
        size: Int,
    ): Result<GetChattingRoomListResponse>

    suspend fun getChattingCrewList(chatRoomId: Long): Result<List<GetChattingCrewListResponse>>

    suspend fun patchChattingRoomImage(
        chatRoomId: Long,
        chatRoomImage: MultipartBody.Part,
    ): Result<PatchChattingRoomImageResponse>

    suspend fun putChattingRoomAlarm(
        chatRoomId: Long,
        enable: Boolean,
    ): Result<PutChattingRoomAlarmResponse>

//    suspend fun deleteChattingRoom(chatRoomId: Long): Result<DeleteChattingRoomResponse>

    suspend fun deleteChattingCrewKickOut(
        chatRoomId: Long,
        userId: Long,
    ): Result<DeleteChattingCrewKickOutResponse>

    suspend fun getChattingMessages(
        chatRoomId: Long,
        lastMessageId: Long?,
        size: Int,
    ): Result<List<GetChattingMessagesResponse>>
}
