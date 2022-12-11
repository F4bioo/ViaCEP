package com.fappslab.viacep.form.presentation.viewmodel

import app.cash.turbine.test
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetRemoteAddressUseCase
import com.fappslab.viacep.form.domain.usecase.SetLocalAddressUseCase
import com.fappslab.viacep.form.presentation.extension.toAddressArgs
import com.fappslab.viacep.form.presentation.model.AddressArgs
import com.fappslab.viacep.lattetools.observable.stateTest
import com.fappslab.viacep.navigation.ZipcodeArgs
import com.fappslab.viacep.remote.stubmockprovider.StubResponse.addressResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class FormViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private lateinit var address: AddressArgs
    private lateinit var initialState: FormViewState

    private val getRemoteAddressUseCase: GetRemoteAddressUseCase = mockk()
    private val getLocalAddressUseCase: GetLocalAddressUseCase = mockk()
    private val setLocalAddressUseCase: SetLocalAddressUseCase = mockk()
    private lateinit var subject: FormViewModel

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getRemoteAddressSuccess Should expose state When onGetRemoteAddress return success result`() {
        // Given
        setupSubject()
        val address = addressResponse.toAddress()
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(address = address.toAddressArgs())
        coEvery { getRemoteAddressUseCase(any()) } returns address

        // When
        subject.onGetRemoteAddress(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getRemoteAddressUseCase(any()) }
    }

    @Test
    fun `getRemoteAddressFailure Should expose state When onGetRemoteAddress return failure result`() {
        // Given
        setupSubject()
        val throwable = Throwable("Error message")
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(
            shouldShowError = true,
            errorMessage = throwable.message
        )
        coEvery { getRemoteAddressUseCase(any()) } throws throwable

        // When
        subject.onGetRemoteAddress(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getRemoteAddressUseCase(any()) }
    }

    @Test
    fun `setLocalAddressSuccess Should expose ClearForm Action When invoke onSetLocalAddress`() {
        // Given
        setupSubject()
        val address = addressResponse.toAddress()
        val expectedAction = FormViewAction.ClearForm
        coEvery { getRemoteAddressUseCase(any()) } returns address
        coEvery { setLocalAddressUseCase(any()) } returns Unit
        runTest { subject.onGetRemoteAddress(zipcode = "01001-000") }

        // When
        subject.onSetLocalAddress()

        // Then
        runTest {
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getRemoteAddressUseCase(any()) }
        coVerify { setLocalAddressUseCase(any()) }
    }

    @Test
    fun `setLocalAddressFailure Should expose state When onSetLocalAddress return failure result`() {
        // Given
        setupSubject()
        val throwable = Throwable("Error message")
        val address = addressResponse.toAddress()
        val expectedState = initialState.copy(
            address = address.toAddressArgs(),
            shouldShowError = true,
            errorMessage = throwable.message
        )
        coEvery { getRemoteAddressUseCase(any()) } returns address
        coEvery { setLocalAddressUseCase(any()) } throws throwable
        runTest { subject.onGetRemoteAddress(zipcode = "01001-000") }

        // When
        subject.onSetLocalAddress()

        // Then
        runTest {
            stateTest(subject) { state ->
                assertEquals(expectedState, state)
            }
        }
        coVerify { getRemoteAddressUseCase(any()) }
        coVerify { setLocalAddressUseCase(any()) }
    }

    @Test
    fun `getLocalAddressSuccess Should expose state When onGetLocalAddress return success result`() {
        val address = addressResponse.toAddress()
        coEvery { getLocalAddressUseCase(any()) } returns address
        setupSubject(shouldMockInitBlock = true)
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(address = address.toAddressArgs())
        coEvery { getLocalAddressUseCase(any()) } returns address

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getLocalAddressUseCase(any()) }
    }

    @Test
    fun `getLocalAddressFailure Should expose state When onGetLocalAddress return failure result`() {
        val throwable = Throwable("Error message")
        coEvery { getLocalAddressUseCase(any()) } throws throwable
        setupSubject(shouldMockInitBlock = true)
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(
            shouldShowError = true,
            errorMessage = throwable.message
        )

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getLocalAddressUseCase(any()) }
    }

    @Test
    fun `finishView Should expose FinishView Action When invoke onFinishView`() {
        // Given
        setupSubject()
        val expectedAction = FormViewAction.FinishView

        // When
        subject.onFinishView()

        // Then
        runTest {
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedZipcode Should expose state When invoke onTextChangedZipcode`() {
        // Given
        setupSubject()
        val address = address.copy(zipcode = "01001-000")
        val expectedState = initialState.copy(address = address)

        // When
        subject.onTextChangedZipcode(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedStreet Should expose state When invoke onTextChangedStreet`() {
        // Given
        setupSubject()
        val address = address.copy(street = "Praça da Sé")
        val expectedState = initialState.copy(address = address)

        // When
        subject.onTextChangedStreet(street = "Praça da Sé")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedDistrict Should expose state When invoke onTextChangedDistrict`() {
        // Given
        setupSubject()
        val address = address.copy(district = "Sé")
        val expectedState = initialState.copy(address = address)

        // When
        subject.onTextChangedDistrict(district = "Sé")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedCity Should expose state When invoke onTextChangedCity`() {
        // Given
        setupSubject()
        val address = address.copy(city = "São Paulo")
        val expectedState = initialState.copy(address = address)

        // When
        subject.onTextChangedCity(city = "São Paulo")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedState Should expose state When invoke onTextChangedState`() {
        // Given
        setupSubject()
        val address = address.copy(state = "SP")
        val expectedState = initialState.copy(address = address)

        // When
        subject.onTextChangedState(state = "SP")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedAreaCode Should expose state When invoke onTextChangedAreaCode`() {
        // Given
        setupSubject()
        val address = address.copy(areaCode = "AreaCode")
        val expectedState = initialState.copy(address = address)

        // When
        subject.onTextChangedAreaCode(areaCode = "AreaCode")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `closeError Should expose state When invoke onCloseError`() {
        // Given
        setupSubject()
        val expectedState = initialState.copy(shouldShowError = false, errorMessage = null)

        // When
        subject.onCloseError()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    private fun setupSubject(shouldMockInitBlock: Boolean = false) {
        val zipcodeArgs = ZipcodeArgs(zipcode = "01001-000")
            .takeIf { shouldMockInitBlock } ?: ZipcodeArgs(zipcode = "")

        subject = FormViewModel(
            args = zipcodeArgs,
            getRemoteAddressUseCase = getRemoteAddressUseCase,
            getLocalAddressUseCase = getLocalAddressUseCase,
            setLocalAddressUseCase = setLocalAddressUseCase
        )
        address = subject.state.value.address
        initialState = FormViewState(address = address)
    }
}
