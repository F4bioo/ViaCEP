package com.fappslab.viacep.register.domain.usecase

import app.cash.turbine.test
import com.fappslab.viacep.register.data.moddel.extension.toAddress
import com.fappslab.viacep.register.domain.repository.RegisterRepository
import com.fappslab.viacep.remote.stubmockprovider.StubResponse.addressResponse
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class GetAddressUseCaseTest {

    private val registerRepository: RegisterRepository = mockk()
    private lateinit var subject: GetAddressUseCase

    @Before
    fun setUp() {
        subject = GetAddressUseCase(
            repository = registerRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAddressSuccess Should emit success result When invoke getAddress`() {
        // Given
        val expectedCovers = addressResponse.toAddress()
        every { registerRepository.getAddress(any()) } returns flowOf(expectedCovers)

        // When
        val result = subject(zipcode = "01001-000")

        // Then
        runBlocking {
            result.test {
                verify(exactly = 1) { registerRepository.getAddress(any()) }
                assertEquals(expectedCovers, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `getAddressFailure Should emit failure result When invoke getAddress`() {
        // Given
        val expectedError = Throwable("Error message")
        every { registerRepository.getAddress(any()) } returns flow { throw expectedError }

        // When
        val result = subject(zipcode = "01001-000")

        // Then
        runBlocking {
            result.test {
                verify(exactly = 1) { registerRepository.getAddress(any()) }
                assertEquals(expectedError.message, awaitError().message)
                expectNoEvents()
            }
        }
    }
}
