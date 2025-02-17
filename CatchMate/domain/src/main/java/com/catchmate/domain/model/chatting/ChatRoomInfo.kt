package com.catchmate.domain.model.chatting

import android.os.Parcelable
import com.catchmate.domain.model.board.Board
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatRoomInfo(
    val chatRoomId: Long,
    val boardInfo: Board,
    val participantCount: Int,
    val lastMessageAt: String?,
    val lastMessageContent: String?,
    val chatRoomImage: String,
) : Parcelable
