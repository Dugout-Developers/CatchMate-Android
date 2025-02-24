package com.catchmate.presentation.view.notification

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.catchmate.domain.NotificationHandler
import com.catchmate.domain.model.enumclass.AcceptState
import com.catchmate.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHandlerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : NotificationHandler {
        override fun createNotificationBuilder(
            data: Map<String, String>,
            title: String,
            body: String,
            notificationBuilder: NotificationCompat.Builder,
        ): NotificationCompat.Builder {
            val args =
                Bundle().apply {
                    if (data.containsKey("acceptStatus")) {
                        putLong("boardId", data["boardId"]?.toLong() ?: -1)
                        putString("acceptStatus", data["acceptStatus"] ?: "")
                    } else {
                        putLong("chatRoomId", data["chatRoomId"]?.toLong() ?: -1)
                    }
                }
            Log.e("args", "${args.getLong("boardId")} ${args.getString("acceptStatus")} ${args.getLong("chatRoomId")}")

            val destinationId =
                when {
                    data["acceptStatus"] == AcceptState.PENDING.name -> R.id.receivedJoinFragment
                    data["acceptStatus"] == AcceptState.ACCEPTED.name -> R.id.chattingHomeFragment
                    data.containsKey("chatRoomId") -> R.id.chattingRoomFragment
                    else -> R.id.homeFragment
                }
            val pendingIntent =
                NavDeepLinkBuilder(context)
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(destinationId)
                    .setArguments(args)
                    .createPendingIntent()

            val icon =
                if (Build.MANUFACTURER.equals("Samsung", true)) {
                    R.drawable.ic_notification_samsung_device
                } else {
                    R.drawable.ic_notification
                }

            val builder =
                notificationBuilder
                    .setAutoCancel(true) // 클릭 시 알림 제거
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentText(body)
                    .setContentTitle(title)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(icon)
                    .setColor(ContextCompat.getColor(context, R.color.brand500))

            return builder
        }
    }
