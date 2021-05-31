package com.brunohensel.cardgame.di

import com.brunohensel.cardgame.home.presentation.ui.HomeActivity
import dagger.Subcomponent

@Subcomponent
interface ActivityComponent {
    fun inject(activity: HomeActivity)
}