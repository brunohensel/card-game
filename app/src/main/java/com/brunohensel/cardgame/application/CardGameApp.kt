package com.brunohensel.cardgame.application

import android.app.Activity
import android.app.Application
import com.brunohensel.cardgame.di.ApplicationComponent
import com.brunohensel.cardgame.di.DaggerApplicationComponent

class CardGameApp: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}
val Activity.component get() = (application as CardGameApp).appComponent