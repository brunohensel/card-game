package com.brunohensel.cardgame.di

import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.di.WarOfSuitsImplWireModule
import com.brunohensel.di.WarOfSuitsPlayerModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, WarOfSuitsImplWireModule::class, WarOfSuitsPlayerModule::class])
interface ApplicationComponent {

    fun activityComponent(): ActivityComponent.Factory
}