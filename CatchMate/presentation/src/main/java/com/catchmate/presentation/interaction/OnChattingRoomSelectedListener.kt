package com.catchmate.presentation.interaction

import com.catchmate.domain.model.chatting.ChatRoomInfo

interface OnChattingRoomSelectedListener {
    fun onChattingRoomSelected(chatRoomInfo: ChatRoomInfo)
}
