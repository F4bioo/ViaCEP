package com.fappslab.viacep.form.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.domain.repository.FormRepository
import com.fappslab.viacep.local.exception.CacheThrowable
import com.fappslab.viacep.remote.utils.stubmockprovider.StubResponse.addressResponse
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
internal class SetLocalAddressUseCaseTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val formRepository: FormRepository = mockk()
    private lateinit var subject: SetLocalAddressUseCase

    @Before
    fun setUp() {
        subject = SetLocalAddressUseCase(
            repository = formRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `setLocalAddressSuccess Should return success result When invoke setAddress`() {
        runTest {
            // Given
            val address = addressResponse.toAddress()
            val expectedResult = Unit
            coEvery { formRepository.setLocalAddress(any()) } returns Unit

            // When
            val result = subject(address)

            // Then
            coVerify { formRepository.setLocalAddress(any()) }
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `setLocalAddressFailure Should return failure result When invoke setAddress`() {
        runTest {
            // Given
            val throwable = CacheThrowable("Error message")
            val address = addressResponse.toAddress()
            coEvery {
                formRepository.setLocalAddress(any())
            } throws throwable

            // When
            val result = assertFailsWith {
                subject(address)
            } as CacheThrowable

            // Then
            coVerify { formRepository.setLocalAddress(any()) }
            assertEquals(throwable.message, result.message)
        }
    }
}
