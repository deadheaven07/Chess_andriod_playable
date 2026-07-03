package com.harsh.chess_application.di

import com.harsh.chess_application.domain.usecase.MoveValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoveValidator(): MoveValidator = MoveValidator()
}
