package com.catchmate.data.di

import com.catchmate.data.repository.BoardLikeRepositoryImpl
import com.catchmate.domain.repository.BoardLikeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BoardLikeModule {
    @Provides
    fun provideBoardLikeRepository(boardLikeRepositoryImpl: BoardLikeRepositoryImpl): BoardLikeRepository = boardLikeRepositoryImpl
}
