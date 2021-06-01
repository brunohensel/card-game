package com.brunohensel.di

import com.brunohensel.WarOfSuits
import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.datasource.local.WarOfSuitLocalDataSource
import com.brunohensel.datasource.local.WarOfSuitLocalDataSourceImpl
import com.brunohensel.datasource.local.deck.Deck
import com.brunohensel.datasource.local.deck.DeckImpl
import com.brunohensel.domain.game.WarOfSuitImpl
import com.brunohensel.domain.rules.SuitsProvider
import com.brunohensel.domain.rules.SuitsProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class WarOfSuitsImplWireModule {
    @Binds
    @ApplicationScope
    internal abstract fun bindsGame(impl: WarOfSuitImpl): WarOfSuits

    @Binds
    @ApplicationScope
    internal abstract fun bindsWarOfSuitLocalDataSource(impl: WarOfSuitLocalDataSourceImpl): WarOfSuitLocalDataSource

    @Binds
    @ApplicationScope
    internal abstract fun bindsSuitsProvider(impl: SuitsProviderImpl): SuitsProvider

    @Binds
    @Singleton
    internal abstract fun bindsDeck(impl: DeckImpl): Deck
}