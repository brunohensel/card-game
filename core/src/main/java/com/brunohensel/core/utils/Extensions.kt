package com.brunohensel.core.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Extension function to collect hot flows safely.
 * As default it will collect flows when the lifecycle of the view is at least on Started, and
 * will stop the collection when the Lifecycle reaches the Stop state.
 *
 * [repeatOnLifecycle] suspends the calling coroutine, re-launches the block when the lifecycle
 * moves in and out of the target state in a new coroutine, and resumes the calling coroutine when
 * the Lifecycle is destroyed.
 *
 * @see https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
 */
fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) = owner.lifecycleScope.launch(coroutineContext) {
    owner.lifecycle.repeatOnLifecycle(minActiveState) {
        this@collectIn.collect()
    }
}