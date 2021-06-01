package com.brunohensel.cardgame.application

import android.app.Activity
import android.app.Application
import com.brunohensel.cardgame.di.ApplicationComponent
import com.brunohensel.cardgame.di.DaggerApplicationComponent
import com.brunohensel.di.WarOfSuitsComponent
import com.brunohensel.di.WarOfSuitsComponentProvider

class CardGameApp: Application(), WarOfSuitsComponentProvider {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

    override fun provideWarOfSuitsComponent(): WarOfSuitsComponent {
        return appComponent.warOfSuitsComponent().create()
    }
}
val Activity.component get() = (application as CardGameApp).appComponent