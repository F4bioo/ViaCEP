package com.fappslab.viacep.form.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.domain.repository.FormRepository
import com.fappslab.viacep.local.exception.CacheThrowable
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
internal class DeleteLocalAddressUseCaseTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val formRepository: FormRepository = mockk()
    private lateinit var subject: DeleteLocalAddressUseCase

    @Before
    fun setUp() {
        subject = DeleteLocalAddressUseCase(
            repository = formRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `deleteAddressSuccess Should return success result When invoke deleteAddress`() {
        runTest {
            // Given
            val expectedResult = Unit
            coEvery { formRepository.deleteLocalAddress(any()) } returns expectedResult

            // When
            val result = subject(zipcode = "01001-000")

            // Then
            coVerify { formRepository.deleteLocalAddress(any()) }
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `deleteAddressFailure Should return failure result When invoke deleteAddress`() {
        runTest {
            // Given
            val throwable = CacheThrowable("Error message")
            coEvery {
                formRepository.deleteLocalAddress(any())
            } throws throwable

            // When
            val result = assertFailsWith {
                subject(zipcode = "01001-000")
            } as CacheThrowable

            // Then
            coVerify { formRepository.deleteLocalAddress(any()) }
            assertEquals(throwable.message, result.message)
        }
    }
}
