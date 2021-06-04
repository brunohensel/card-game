package com.brunohensel.test.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals

fun <T> Flow<T>.test(scope: CoroutineScope): TestObserver<T> = TestObserver(scope, this)

class TestObserver<T>(scope: CoroutineScope, flow: Flow<T>) {

    private val values = mutableListOf<T>()

    private val job: Job = scope.launch {
        flow.collect { values.add(it) }
    }

    fun assertFirst(expected: T): TestObserver<T> = apply { assertEquals(values.first(), expected) }

    fun assertLast(expected: T): TestObserver<T> = apply { assertEquals(values.last(), expected) }

    fun assertValueAt(index: Int, expected: T): TestObserver<T> = apply { assertEquals(values[index], expected) }

    fun finish() {
        job.cancel()
    }
}