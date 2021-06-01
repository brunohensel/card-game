package com.brunohensel.core.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
/**
 * Base ViewModel that is responsible to emit view events, map them into view state and emit them
 * back to the observer, acting like a bridge between your higher layers and your view.
 *
 * This class extends the architecture component ViewModel, which survives configuration changes.
 *
 * @param [State] is the result from processing the event dispatched by the view.
 * @param [Events] is represents basically the interaction from the user.
 * @param [initialState] is the first state when the view is shown.
 */
@OptIn(FlowPreview::class)
abstract class BaseStateViewModel<State, Events>(initialState: State, initialEvent: Events) : ViewModel() {

    //produce a flow from events
    private val events = MutableStateFlow(initialEvent)

    /**
     * This property holds a chain of flow of [State] and will be collected in the UI every time a
     * new State was emitted.
     *
     * The [stateIn] converts a cold flow into a hot flow and prevents the same, based on
     * equals, from being is emitted twice in a row.
     *
     * [WhileSubscribed] will keep the upstream flow active after the last collector disappears.
     * That prevents the upstream from restarting itself after the user rotates the device.
     *
     * [initialState] is the first state emitted by this hot flow, usually is a Idle state.
     */
    val state: StateFlow<State> = toState()
        .stateIn(viewModelScope,
        WhileSubscribed(2000), initialState)

    private fun toState(): Flow<State> {
        return events
            .onEach { Log.d("EVENTS", "Event: $it") }
            .map { process(it) }
            .flattenMerge()
    }

    /**
     * This method is implemented by the class that extends this viewModel in order to process the
     * events produced in the UI layer, emitting as a result a flow of [State] that will be collected
     * in the view.
     */
    abstract fun process(event: Events): Flow<State>

    /**
     * Capture the user interaction in form of events, and pass them to the [MutableSharedFlow], which
     * will emit them to start the reactive chain.
     */
    fun dispatch(event: Events) {
        viewModelScope.launch {
            events.emit(event)
        }
    }
}