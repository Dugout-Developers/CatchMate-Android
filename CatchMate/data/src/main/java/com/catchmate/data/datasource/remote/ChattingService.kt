package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.chatting.ChatRoomInfoDTO
import com.catchmate.data.dto.chatting.DeleteChattingRoomResponseDTO
import com.catchmate.data.dto.chatting.GetChattingCrewListResponseDTO
import com.catchmate.data.dto.chatting.GetChattingHistoryResponseDTO
import com.catchmate.data.dto.chatting.GetChattingRoomListResponseDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChattingService {
    @GET("chat-rooms/list")
    suspend fun getChattingRoomList(
        @Query("page") page: Int,
    ): Response<GetChattingRoomListResponseDTO?>

    @GET("chat-rooms/{chatRoomId}/user-list")
    suspend fun getChattingCrewList(
        @Path("chatRoomId") chatRoomId: Long,
    ): Response<GetChattingCrewListResponseDTO?>

    @GET("chat-rooms/{chatRoomId}")
    suspend fun getChattingRoomInfo(
        @Path("chatRoomId") chatRoomId: Long,
    ): Response<ChatRoomInfoDTO?>

    @DELETE("chat-rooms/{chatRoomId}")
    suspend fun deleteChattingRoom(
        @Path("chatRoomId") chatRoomId: Long,
    ): Response<DeleteChattingRoomResponseDTO?>

    @GET("chats/{chatRoomId}")
    suspend fun getChattingHistory(
        @Path("chatRoomId") chatRoomId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int?, // default = 20
    ): Response<GetChattingHistoryResponseDTO?>
}
