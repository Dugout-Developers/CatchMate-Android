package com.catchmate.data.mapper

import com.catchmate.data.dto.chatting.ChatRoomInfoDTO
import com.catchmate.data.dto.chatting.GetChattingCrewListResponseDTO
import com.catchmate.data.dto.chatting.GetChattingMessagesResponseDTO
import com.catchmate.data.dto.chatting.GetChattingRoomListResponseDTO
import com.catchmate.data.dto.chatting.LastMessageInfoDto
import com.catchmate.data.dto.chatting.PutChattingRoomAlarmResponseDTO
import com.catchmate.data.mapper.BoardMapper.toBoard
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.GetChattingMessagesResponse
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.model.chatting.LastMessageInfo
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

    fun toGetChattingHistoryResponse(dto: GetChattingMessagesResponseDTO): GetChattingMessagesResponse =
        GetChattingMessagesResponse(
            messageId = dto.messageId,
            chatRoomId = dto.chatRoomId,
            senderId = dto.senderId,
            senderNickName = dto.senderNickName,
            senderProfileImageUrl = dto.senderProfileImageUrl,
            content = dto.content,
            messageType = dto.messageType,
            createdAt = dto.createdAt,
        )

    fun toGetChattingCrewListResponse(dto: GetChattingCrewListResponseDTO): GetChattingCrewListResponse =
        GetChattingCrewListResponse(
            memberId = dto.memberId,
            userId = dto.userId,
            nickName = dto.nickName,
            profileImageUrl = dto.profileImageUrl,
            joinedAt = dto.joinedAt,
        )

    fun toPutChattingRoomAlarmResponse(dto: PutChattingRoomAlarmResponseDTO): PutChattingRoomAlarmResponse =
        PutChattingRoomAlarmResponse(
            state = dto.state,
        )
}
