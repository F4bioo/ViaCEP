package com.fappslab.viacep.form.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.domain.repository.FormRepository
import com.fappslab.viacep.local.exception.CacheThrowable
import com.fappslab.viacep.remote.stubmockprovider.StubResponse.addressResponse
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
internal class GetLocalAddressUseCaseTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val formRepository: FormRepository = mockk()
    private lateinit var subject: GetLocalAddressUseCase

    @Before
    fun setUp() {
        subject = GetLocalAddressUseCase(
            repository = formRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getLocalAddressSuccess Should return success result When invoke getAddress`() {
        runTest {
            // Given
            val expectedResult = addressResponse.toAddress()
            coEvery { formRepository.getLocalAddress(any()) } returns expectedResult

            // When
            val result = subject(zipcode = "01001-000")

            // Then
            coVerify { formRepository.getLocalAddress(any()) }
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getLocalAddressFailure Should return failure result When invoke getAddress`() {
        runTest {
            // Given
            val throwable = CacheThrowable("Error message")
            coEvery {
                formRepository.getLocalAddress(any())
            } throws throwable

            // When
            val result = assertFailsWith {
                subject(zipcode = "01001-000")
            } as CacheThrowable

            // Then
            coVerify { formRepository.getLocalAddress(any()) }
            assertEquals(throwable.message, result.message)
        }
    }
}
