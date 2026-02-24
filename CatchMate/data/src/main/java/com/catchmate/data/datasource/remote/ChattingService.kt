package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.chatting.DeleteChattingCrewKickOutResponseDTO
import com.catchmate.data.dto.chatting.GetChattingCrewListResponseDTO
import com.catchmate.data.dto.chatting.GetChattingMessagesResponseDTO
import com.catchmate.data.dto.chatting.GetChattingRoomListResponseDTO
import com.catchmate.data.dto.chatting.PatchChattingRoomImageResponseDTO
import com.catchmate.data.dto.chatting.PutChattingRoomAlarmResponseDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ChattingService {
    @GET("api/chat/rooms")
    suspend fun getChattingRoomList(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<GetChattingRoomListResponseDTO?>

    @GET("api/chat/rooms/{chatRoomId}/members")
    suspend fun getChattingCrewList(
        @Path("chatRoomId") chatRoomId: Long,
    ): Response<List<GetChattingCrewListResponseDTO>?>

    @Multipart
    @PATCH("chat-rooms/{chatRoomId}/image")
    suspend fun patchChattingRoomImage(
        @Path("chatRoomId") chatRoomId: Long,
        @Part chatRoomImage: MultipartBody.Part,
    ): Response<PatchChattingRoomImageResponseDTO?>

    @PUT("chat-rooms/{chatRoomId}/notification")
    suspend fun putChattingRoomAlarm(
        @Path("chatRoomId") chatRoomId: Long,
        @Query("enable") enable: Boolean,
    ): Response<PutChattingRoomAlarmResponseDTO?>

    @DELETE("api/chat/rooms/{roomId}")
    suspend fun deleteChattingRoom(
        @Path("roomId") roomId: Long,
    ): Response<Unit>

    @DELETE("chat-rooms/{chatRoomId}/users/{userId}")
    suspend fun deleteChattingCrewKickOut(
        @Path("chatRoomId") chatRoomId: Long,
        @Path("userId") userId: Long,
    ): Response<DeleteChattingCrewKickOutResponseDTO?>

    @GET("api/chat/rooms/{roomId}/messages")
    suspend fun getChattingMessages(
        @Path("roomId") roomId: Long,
        @Query("lastMessageId") lastMessageId: Long?,
        @Query("size") size: Int, // default = 20
    ): Response<List<GetChattingMessagesResponseDTO>?>
}
