package com.brunohensel.core.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
/**
 * Base ViewModel that is responsible to emit view events, map them into view state and emit them
 * back to the observer.
 */
@ExperimentalCoroutinesApi
abstract class BaseStateViewModel<State, Events>(initialState: State) : ViewModel() {

    //producer of view events
    private val events = MutableSharedFlow<Events>()

    //State that will be collected in the view
    val state: StateFlow<State> = toState()
        .stateIn(viewModelScope,
        WhileSubscribed(5000), initialState)


    @OptIn(FlowPreview::class)
    private fun toState(): Flow<State> {
        return events
            .map { process(it) }
            .flattenMerge()
    }

    abstract fun process(event: Events): Flow<State>

    fun dispatch(event: Events) {
        viewModelScope.launch {
            events.emit(event)
        }
    }
}