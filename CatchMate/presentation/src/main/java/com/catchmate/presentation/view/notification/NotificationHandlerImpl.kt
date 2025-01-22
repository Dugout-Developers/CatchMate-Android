package com.catchmate.presentation.view.notification

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.catchmate.domain.NotificationHandler
import com.catchmate.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NotificationHandler {
    override fun createNotificationBuilder(
        data: Map<String, String>,
        title: String,
        body: String,
        notificationBuilder: NotificationCompat.Builder
    ): NotificationCompat.Builder {
        val args = Bundle().apply {
            putLong("boardId", data["boardId"]?.toLong() ?: 0)
            putString("acceptStatus", data["acceptStatus"] ?: "")
        }
        Log.e("args", "${args.getLong("boardId")} ${args.getString("acceptStatus")}")

        val destinationId =
            when (data["acceptStatus"]) {
                "PENDING" -> R.id.receivedJoinFragment
                "ACCEPTED" -> R.id.chattingHomeFragment
                else -> R.id.homeFragment
            }
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(destinationId)
            .setArguments(args)
            .createPendingIntent()

        val icon = if (Build.MANUFACTURER.equals("Samsung", true)) {
                R.drawable.ic_notification_samsung_device
            } else {
                R.drawable.ic_notification
            }

        val builder = notificationBuilder
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(body)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setSmallIcon(icon)
            .setColor(ContextCompat.getColor(context, R.color.brand500))

        return builder
    }
}
