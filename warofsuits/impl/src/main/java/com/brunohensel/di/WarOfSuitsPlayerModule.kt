package com.brunohensel.di

import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.model.Player
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class WarOfSuitsPlayerModule {
    @ApplicationScope
    @Provides
    @Named("Player1")
    fun providePlayerOne() = Player(name = "Player one")

    @ApplicationScope
    @Provides
    @Named("Player2")
    fun providePlayerTwo() = Player(name = "Player two")
}