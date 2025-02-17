package com.catchmate.data.di

import com.catchmate.data.datasource.remote.ChatWebSocketClient
import com.catchmate.data.repository.ChatWebSocketRepositoryImpl
import com.catchmate.domain.repository.ChatWebSocketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatWebSocketModule {
    @Provides
    fun provideChatWebSocketRepository(chatWebSocketRepositoryImpl: ChatWebSocketRepositoryImpl): ChatWebSocketRepository = chatWebSocketRepositoryImpl

    @Provides
    @Singleton
    fun provideChatWebSocketClient(): ChatWebSocketClient = ChatWebSocketClient()
}
