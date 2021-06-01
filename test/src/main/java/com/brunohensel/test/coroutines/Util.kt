package com.brunohensel.test.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun testCoroutine(context: CoroutineContext = EmptyCoroutineContext, testBody: suspend TestCoroutineScope.() -> Unit) =
    runBlockingTest(context, testBody)