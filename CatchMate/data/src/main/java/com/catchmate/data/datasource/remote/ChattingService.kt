package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.chatting.GetChattingRoomListResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface ChattingService {
    @GET("chat-rooms/list")
    suspend fun getChattingRoomList(): Response<GetChattingRoomListResponseDTO?>
}
