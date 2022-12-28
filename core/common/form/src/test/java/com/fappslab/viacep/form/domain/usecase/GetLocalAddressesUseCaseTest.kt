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
internal class GetLocalAddressesUseCaseTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val formRepository: FormRepository = mockk()
    private lateinit var subject: GetLocalAddressesUseCase

    @Before
    fun setUp() {
        subject = GetLocalAddressesUseCase(
            repository = formRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getLocalAddressesSuccess Should return success result When invoke getAddresses`() {
        runTest {
            // Given
            val expectedResult = listOf(addressResponse.toAddress())
            coEvery { formRepository.getLocalAddresses() } returns expectedResult

            // When
            val result = subject()

            // Then
            coVerify { formRepository.getLocalAddresses() }
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getLocalAddressesFailure Should return failure result When invoke getAddresses`() {
        runTest {
            // Given
            val throwable = CacheThrowable("Error message")
            coEvery {
                formRepository.getLocalAddresses()
            } throws throwable

            // When
            val result = assertFailsWith {
                subject()
            } as CacheThrowable

            // Then
            coVerify { formRepository.getLocalAddresses() }
            assertEquals(throwable.message, result.message)
        }
    }
}
