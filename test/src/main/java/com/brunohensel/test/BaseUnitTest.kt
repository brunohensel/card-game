package com.brunohensel.test

import androidx.annotation.CallSuper
import com.brunohensel.test.rules.CoroutineRules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

abstract class BaseUnitTest<T : Any> {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesRule = CoroutineRules()

    protected lateinit var tested: T

    @Before
    @CallSuper
    open fun setup() {
        tested = sut()
    }

    //acronym for system under test
    protected abstract fun sut(): T
}