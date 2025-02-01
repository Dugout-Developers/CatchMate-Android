package com.catchmate.domain.repository

import com.catchmate.domain.model.chatting.GetChattingRoomListResponse

interface ChattingRepository {
    suspend fun getChattingRoomList(): Result<GetChattingRoomListResponse>
}
