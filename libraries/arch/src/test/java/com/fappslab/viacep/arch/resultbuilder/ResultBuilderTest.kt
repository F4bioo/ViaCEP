package com.fappslab.viacep.arch.resultbuilder

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ResultBuilderTest {

    @Test
    fun `getStates Should assert first and final state When invoke onStart and onComplete`() {
        var state: String? = null
        val expectedResult = "Start loading state: true. Completion loading state: false."

        // When
        runTest {
            ResultBuilder.runAsyncSafely {
            }.onStart {
                state = "Start loading state: true. "
            }.onCompletion {
                state += "Completion loading state: false."
            }.build()
        }

        // Then
        assertEquals(expectedResult, state)
    }

    @Test
    fun `getFailure Should assert failure When invoke onFailure`() {
        var cause: Throwable? = null
        val expectedThrowable = Throwable("Error message.")

        // When
        runTest {
            ResultBuilder.runAsyncSafely {
                throw expectedThrowable
            }.onFailure {
                cause = it
            }.build()
        }

        // Then
        assertEquals(expectedThrowable.message, cause?.message)
    }

    @Test
    fun `getSuccess Should assert success When invoke onSuccess`() {
        // Given
        var result: String? = null
        val expectedResult = "Some data."

        // When
        runTest {
            ResultBuilder.runAsyncSafely {
                expectedResult
            }.onSuccess {
                result = it
            }.build()
        }

        // Then
        assertEquals(expectedResult, result)
    }
}
