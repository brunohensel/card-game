package com.brunohensel.cardgame.di

import com.brunohensel.core.annotations.ApplicationScope
import com.brunohensel.di.WarOfSuitsComponent
import com.brunohensel.di.WarOfSuitsImplWireModule
import com.brunohensel.di.WarOfSuitsPlayerModule
import com.brunohensel.di.WarOfSuitsSubcomponentModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, WarOfSuitsImplWireModule::class, WarOfSuitsPlayerModule::class, WarOfSuitsSubcomponentModule::class, ActivityModule::class])
interface ApplicationComponent {

    fun warOfSuitsComponent(): WarOfSuitsComponent.Factory
    fun activityComponent(): ActivityComponent.Factory
}