package com.catchmate.data.mapper

import com.catchmate.data.dto.chatting.ChatRoomInfoDTO
import com.catchmate.data.dto.chatting.GetChattingRoomListResponseDTO
import com.catchmate.data.mapper.BoardMapper.toBoard
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse

object ChattingMapper {
    fun toGetChattingRoomListResponse(dto: GetChattingRoomListResponseDTO): GetChattingRoomListResponse =
        GetChattingRoomListResponse(
            chatRoomInfoList = dto.chatRoomInfoList.map { toChatRoomInfo(it) },
            totalPages = dto.totalPages,
            totalElements = dto.totalElements,
            isFirst = dto.isFirst,
            isLast = dto.isLast,
        )

    private fun toChatRoomInfo(dto: ChatRoomInfoDTO): ChatRoomInfo =
        ChatRoomInfo(
            chatRoomId = dto.chatRoomId,
            boardInfo = toBoard(dto.boardInfo),
            participantCount = dto.participantCount,
            lastMessageAt = dto.lastMessageAt,
            lastMessageContent = dto.lastMessageContent,
            chatRoomImage = dto.chatRoomImage,
        )
}
