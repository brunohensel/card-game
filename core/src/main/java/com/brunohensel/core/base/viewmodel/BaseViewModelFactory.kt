package com.brunohensel.core.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Factory to be used for viewModels with parameterized constructor when using Dagger.
 * This will create a map that will be used to retrieve a viewModel object which will be
 * lazily initialized thanks to [Provider] interface.
 *
 * The [@JvmSuppressWildcards] prevents Kotlin to generate a Provider<? extends ViewModel>
 */
@Singleton
class BaseViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}