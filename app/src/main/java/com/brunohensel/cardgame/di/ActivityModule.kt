package com.brunohensel.cardgame.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunohensel.cardgame.home.datasource.remote.AvailableGameRemoteImpl
import com.brunohensel.cardgame.home.domain.AvailableGameRemote
import com.brunohensel.cardgame.home.presentation.HomeViewModel
import com.brunohensel.core.base.viewmodel.BaseViewModelFactory
import com.brunohensel.core.base.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    abstract fun bindRemoteDataSource(impl: AvailableGameRemoteImpl): AvailableGameRemote

    @Binds
    abstract fun bindBaseViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory
}