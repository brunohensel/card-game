package com.brunohensel.core.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Provider

/**
 * Factory to be used for viewModels with parameterized constructor when using Dagger.
 * This will create a map that will be used to retrieve a viewModel object which will be
 * lazily initialized thanks to [Provider] interface.
 *
 * @param [creators] is a Map<key, value> where key is the viewModel class reference and the value is
 * an instance of the viewModel.
 * The [@JvmSuppressWildcards] prevents Kotlin to generate a Provider<? extends ViewModel>
 * @return an fully-constructed and injected instance of the viewModel
 *
 *
 */
@Reusable
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