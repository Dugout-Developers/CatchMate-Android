package com.catchmate.domain.model.chatting

import android.os.Parcelable
import com.catchmate.domain.model.board.Board
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatRoomInfo(
    val chatRoomId: Long,
    val board: Board,
    val lastMessage: LastMessageInfo?,
    val unreadCount: Long,
    val chatRoomImageUrl: String?,
    val createdAt: String,
    val notificationOn: Boolean,
) : Parcelable
