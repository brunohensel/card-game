package com.brunohensel.cardgame.di

import com.brunohensel.core.annotations.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun activityComponent(): ActivityComponent.Factory
}