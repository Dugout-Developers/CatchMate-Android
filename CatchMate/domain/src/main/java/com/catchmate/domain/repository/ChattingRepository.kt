package com.catchmate.domain.repository

import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.DeleteChattingCrewKickOutResponse
import com.catchmate.domain.model.chatting.DeleteChattingRoomResponse
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse

interface ChattingRepository {
    suspend fun getChattingRoomList(page: Int): Result<GetChattingRoomListResponse>

    suspend fun getChattingCrewList(chatRoomId: Long): Result<GetChattingCrewListResponse>

    suspend fun getChattingRoomInfo(chatRoomId: Long): Result<ChatRoomInfo>

    suspend fun deleteChattingRoom(chatRoomId: Long): Result<DeleteChattingRoomResponse>

    suspend fun deleteChattingCrewKickOut(
        chatRoomId: Long,
        userId: Long,
    ): Result<DeleteChattingCrewKickOutResponse>

    suspend fun getChattingHistory(
        chatRoomId: Long,
        page: Int,
        size: Int?,
    ): Result<GetChattingHistoryResponse>
}
