package com.brunohensel.cardgame.di

import androidx.lifecycle.ViewModelProvider
import com.brunohensel.core.base.viewmodel.BaseViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindBaseViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory
}