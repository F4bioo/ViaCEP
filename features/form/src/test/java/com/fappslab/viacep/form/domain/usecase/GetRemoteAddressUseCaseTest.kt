package com.fappslab.viacep.form.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.domain.repository.FormRepository
import com.fappslab.viacep.remote.exception.ApplicationThrowable.ApiServiceThrowable
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
internal class GetRemoteAddressUseCaseTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val formRepository: FormRepository = mockk()
    private lateinit var subject: GetRemoteAddressUseCase

    @Before
    fun setUp() {
        subject = GetRemoteAddressUseCase(
            repository = formRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getRemoteAddressSuccess Should return success result When invoke getAddress`() {
        runTest {
            // Given
            val expectedResult = addressResponse.toAddress()
            coEvery { formRepository.getRemoteAddress(any()) } returns expectedResult

            // When
            val result = subject(zipcode = "01001-000")

            // Then
            coVerify(exactly = 1) { formRepository.getRemoteAddress(any()) }
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getRemoteAddressFailure Should return error When invoke getAddress with blank param`() {
        runTest {
            // Given
            val expectedResult = ERROR_MESSAGE

            // When
            val result = assertFailsWith<IllegalArgumentException> { subject(zipcode = "") }

            // Then
            assertEquals(expectedResult, result.message)
        }
    }

    @Test
    fun `getRemoteAddressFailure Should return failure result When invoke getAddress`() {
        runTest {
            // Given
            val expectedResult = true
            coEvery {
                formRepository.getRemoteAddress(any())
            } throws ApiServiceThrowable(error = true)

            // When
            val result = assertFailsWith<ApiServiceThrowable> { subject(zipcode = "01001-000") }

            // Then
            coVerify(exactly = 1) { formRepository.getRemoteAddress(any()) }
            assertEquals(expectedResult, result.error)
        }
    }
}
