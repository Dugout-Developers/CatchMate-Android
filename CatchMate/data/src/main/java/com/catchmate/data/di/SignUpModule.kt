package com.catchmate.data.di

import com.catchmate.data.repository.SignUpRepositoryImpl
import com.catchmate.domain.repository.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SignUpModule {
    @Provides
    fun provideSignUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository = signUpRepositoryImpl
}
