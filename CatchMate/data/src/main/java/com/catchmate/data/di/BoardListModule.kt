package com.catchmate.data.di

import com.catchmate.data.repository.BoardListRepositoryImpl
import com.catchmate.domain.repository.BoardListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BoardListModule {
    @Provides
    fun provideBoardListRepository(boardListRepositoryImpl: BoardListRepositoryImpl): BoardListRepository = boardListRepositoryImpl
}
