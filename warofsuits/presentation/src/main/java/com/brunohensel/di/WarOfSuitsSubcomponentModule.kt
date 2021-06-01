package com.brunohensel.di

import androidx.lifecycle.ViewModel
import com.brunohensel.core.base.viewmodel.ViewModelKey
import com.brunohensel.presentation.WarOfSuitsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WarOfSuitsSubcomponentModule{
    @Binds
    @IntoMap
    @ViewModelKey(WarOfSuitsViewModel::class)
    abstract fun bindWarOfSuitsViewModel(vm: WarOfSuitsViewModel): ViewModel
}