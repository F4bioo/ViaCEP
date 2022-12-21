package com.fappslab.viacep.form.presentation.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.test.assertEquals

class TestObserver<T>(private val flow: StateFlow<T>) {
    private val expectedStates = mutableListOf<T>()

    fun expect(vararg states: T) {
        expectedStates.addAll(states)
    }

    fun expect(states: List<T>) {
        expectedStates.addAll(states)
    }

    suspend fun verify(timeout: Long = 1000L) {
        flow.test {
            expectedStates.forEach { expectedState ->
                val actualState = withTimeoutOrNull(timeout) {
                    awaitItem()
                }
                assertEquals(expectedState, actualState)
            }
            cancelAndConsumeRemainingEvents()
        }
    }
}
