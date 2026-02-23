package com.catchmate.domain.model.chatting

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LastMessageInfo(
    val messageId: Long,
    val chatRoomId: Long,
    val senderId: Long,
    val senderNickName: String,
    val senderProfileImageUrl: String,
    val content: String,
    val messageType: String,
    val createdAt: String,
) : Parcelable
