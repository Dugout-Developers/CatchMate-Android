package com.catchmate.data.mapper

import com.catchmate.data.dto.chatting.ChatMessageIdDTO
import com.catchmate.data.dto.chatting.ChatMessageInfoDTO
import com.catchmate.data.dto.chatting.ChatRoomInfoDTO
import com.catchmate.data.dto.chatting.DeleteChattingCrewKickOutResponseDTO
import com.catchmate.data.dto.chatting.DeleteChattingRoomResponseDTO
import com.catchmate.data.dto.chatting.GetChattingCrewListResponseDTO
import com.catchmate.data.dto.chatting.GetChattingHistoryResponseDTO
import com.catchmate.data.dto.chatting.GetChattingRoomListResponseDTO
import com.catchmate.data.dto.chatting.LastMessageInfoDto
import com.catchmate.data.dto.chatting.PatchChattingRoomImageResponseDTO
import com.catchmate.data.dto.chatting.PutChattingRoomAlarmResponseDTO
import com.catchmate.data.mapper.BoardMapper.toBoard
import com.catchmate.data.mapper.UserMapper.toGetUserProfileResponse
import com.catchmate.domain.model.chatting.ChatMessageId
import com.catchmate.domain.model.chatting.ChatMessageInfo
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.DeleteChattingCrewKickOutResponse
import com.catchmate.domain.model.chatting.DeleteChattingRoomResponse
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingHistoryResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.model.chatting.LastMessageInfo
import com.catchmate.domain.model.chatting.PatchChattingRoomImageResponse
import com.catchmate.domain.model.chatting.PutChattingRoomAlarmResponse

object ChattingMapper {
    fun toGetChattingRoomListResponse(dto: GetChattingRoomListResponseDTO): GetChattingRoomListResponse =
        GetChattingRoomListResponse(
            content = dto.content.map { toChatRoomInfo(it) },
            pageNumber = dto.pageNumber,
            totalPages = dto.totalPages,
            totalElements = dto.totalElements,
            hasNext = dto.hasNext,
        )

    fun toChatRoomInfo(dto: ChatRoomInfoDTO): ChatRoomInfo =
        ChatRoomInfo(
            chatRoomId = dto.chatRoomId,
            board = toBoard(dto.board),
            lastMessage = toLastMessageInfo(dto.lastMessage),
            unreadCount = dto.unreadCount,
            createdAt = dto.createdAt,
        )

    private fun toLastMessageInfo(dto: LastMessageInfoDto?): LastMessageInfo? =
        dto?.let {
            LastMessageInfo(
                messageId = dto.messageId,
                chatRoomId = dto.chatRoomId,
                senderId = dto.senderId,
                senderNickName = dto.senderNickName,
                senderProfileImageUrl = dto.senderProfileImageUrl,
                content = dto.content,
                messageType = dto.messageType,
                createdAt = dto.createdAt,
            )
        }

    fun toGetChattingHistoryResponse(dto: GetChattingHistoryResponseDTO): GetChattingHistoryResponse =
        GetChattingHistoryResponse(
            chatMessageInfoList = dto.chatMessageInfoList.map { toChatMessageInfo(it) },
            isFirst = dto.isFirst,
            isLast = dto.isLast,
            lastMessageId = dto.lastMessageId,
        )

    private fun toChatMessageInfo(dto: ChatMessageInfoDTO): ChatMessageInfo =
        ChatMessageInfo(
            id = toChatMessageId(dto.id),
            chatMessageId = dto.chatMessageId,
            roomId = dto.roomId,
            content = dto.content,
            senderId = dto.senderId,
            messageType = dto.messageType,
        )

    private fun toChatMessageId(dto: ChatMessageIdDTO): ChatMessageId =
        ChatMessageId(
            timestamp = dto.timestamp,
            date = dto.date,
        )

    fun toGetChattingCrewListResponse(dto: GetChattingCrewListResponseDTO): GetChattingCrewListResponse =
        GetChattingCrewListResponse(
            userInfoList = dto.userInfoList.map { toGetUserProfileResponse(it) },
        )

    fun toDeleteChattingRoomResponse(dto: DeleteChattingRoomResponseDTO): DeleteChattingRoomResponse =
        DeleteChattingRoomResponse(
            state = dto.state,
        )

    fun toDeleteChattingCrewKickOutResponse(dto: DeleteChattingCrewKickOutResponseDTO): DeleteChattingCrewKickOutResponse =
        DeleteChattingCrewKickOutResponse(
            state = dto.state,
        )

    fun toPatchChattingRoomImageResponse(dto: PatchChattingRoomImageResponseDTO): PatchChattingRoomImageResponse =
        PatchChattingRoomImageResponse(
            state = dto.state,
        )

    fun toPutChattingRoomAlarmResponse(dto: PutChattingRoomAlarmResponseDTO): PutChattingRoomAlarmResponse =
        PutChattingRoomAlarmResponse(
            state = dto.state,
        )
}
