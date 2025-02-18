package com.catchmate.domain.repository

import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse

interface ChattingRepository {
    suspend fun getChattingRoomList(page: Int): Result<GetChattingRoomListResponse>

    suspend fun getChattingCrewList(chatRoomId: Long): Result<GetChattingCrewListResponse>

    suspend fun getChattingHistory(
        chatRoomId: Long,
        page: Int,
        size: Int?,
    ): Result<GetChattingHistoryResponse>
}
