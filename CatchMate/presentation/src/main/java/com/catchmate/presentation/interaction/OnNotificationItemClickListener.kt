package com.catchmate.presentation.interaction

interface OnNotificationItemClickListener {
    fun onNotificationItemClick(
        notificationId: Long,
        currentPos: Int,
    )
}
