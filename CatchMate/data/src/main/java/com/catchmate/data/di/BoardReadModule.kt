package com.catchmate.data.di

import com.catchmate.data.repository.BoardReadRepositoryImpl
import com.catchmate.domain.repository.BoardReadRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BoardReadModule {
    @Provides
    fun provideBoardReadRepository(boardReadRepositoryImpl: BoardReadRepositoryImpl): BoardReadRepository = boardReadRepositoryImpl
}
