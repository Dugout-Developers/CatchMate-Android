package com.catchmate.data.datasource.remote

import com.catchmate.data.BuildConfig
import com.gmail.bishoybasily.stomp.lib.StompClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class ChatWebSocketClient
    @Inject
    constructor() {
        private val intervalMillis = 1000L

        private val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        private val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val stompClient = StompClient(okHttpClient, intervalMillis).apply {
            url = BuildConfig.SERVER_SOCKET_URL
        }
}
