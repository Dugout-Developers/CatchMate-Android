package com.catchmate.data.di

import com.catchmate.data.repository.BoardWriteRepositoryImpl
import com.catchmate.domain.repository.BoardWriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BoardWriteModule {
    @Provides
    fun provideBoardWriteRepository(boardWriteRepositoryImpl: BoardWriteRepositoryImpl): BoardWriteRepository = boardWriteRepositoryImpl
}
