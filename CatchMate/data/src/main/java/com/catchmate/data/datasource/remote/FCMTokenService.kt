package com.catchmate.data.datasource.remote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.catchmate.domain.NotificationHandler
import com.google.firebase.messaging.Constants.MessageNotificationKeys
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class FCMTokenService : FirebaseMessagingService() {
    @Inject
    lateinit var notificationHandler: NotificationHandler
    lateinit var body: String
    lateinit var title: String

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("newToken", "$token")
    }

    override fun handleIntent(intent: Intent?) {
        body = intent?.extras?.getString("gcm.notification.body") ?: ""
        title = intent?.extras?.getString("gcm.notification.title") ?: ""
        Log.e("body title", "$body - $title")
        val new = intent?.apply {
            val temp = extras?.apply {
                remove(MessageNotificationKeys.ENABLE_NOTIFICATION)
                remove("gcm.notification.e")
            }
            replaceExtras(temp)
        }
        super.handleIntent(new)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("MESSAGE RECEIVED", "+++++++")

        val data = message.data

        Log.e("MSG", "${data["boardId"]} / ${data["acceptStatus"]} / $title / $body")
        if (title.isNotEmpty() && body.isNotEmpty()) {
            showNotification(data, title, body)
        }
    }

    private fun getNotificationBuilder(channelId: String) : NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this@FCMTokenService, channelId)
        } else {
            NotificationCompat.Builder(this@FCMTokenService)
        }
    }

    private fun createNotificationChannel(
        channerId: String,
        channerName: String,
        notificationBuilder: NotificationCompat.Builder,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel = notificationManager.getNotificationChannel(channerId)

            if (channel == null) {
                val newChannel =
                    NotificationChannel(
                        channerId,
                        channerName,
                        NotificationManager.IMPORTANCE_HIGH,
                    )
                newChannel.enableVibration(true)
                notificationManager.createNotificationChannel(newChannel)
            }
            val requestCode = UUID.randomUUID().hashCode()
            notificationManager.notify(requestCode, notificationBuilder.build())
        }
    }

    private fun showNotification(
        data: Map<String, String>,
        title: String,
        body: String,
    ) {
        val notificationBuilder = getNotificationBuilder(PUST_NOTIFICATION_CHANNEL_ID)
        val builder = notificationHandler.createNotificationBuilder(data, title, body, notificationBuilder)
        createNotificationChannel(PUST_NOTIFICATION_CHANNEL_ID, PUST_NOTIFICATION_CHANNEL_NAME, builder)
    }

    suspend fun getToken(): String = FirebaseMessaging.getInstance().token.await()

    companion object {
        const val PUST_NOTIFICATION_CHANNEL_ID = "CatchmateNotificationChannel"
        const val PUST_NOTIFICATION_CHANNEL_NAME = "CatchmateNotificationChannel"
    }
}
