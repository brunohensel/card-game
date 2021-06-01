package com.brunohensel.di

import com.brunohensel.core.annotations.ActivityScope
import com.brunohensel.presentation.WarOfSuitsActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface WarOfSuitsComponent {
    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): WarOfSuitsComponent
    }

    fun inject(activity: WarOfSuitsActivity)
}