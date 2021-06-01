package com.brunohensel.cardgame.di

import com.brunohensel.cardgame.home.presentation.ui.HomeActivity
import com.brunohensel.core.annotations.ActivityScope
import dagger.Subcomponent

@Subcomponent
@ActivityScope
interface ActivityComponent {
    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
    fun inject(activity: HomeActivity)
}