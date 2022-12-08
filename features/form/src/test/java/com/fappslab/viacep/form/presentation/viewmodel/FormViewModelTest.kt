package com.fappslab.viacep.form.presentation.viewmodel

import app.cash.turbine.test
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.domain.usecase.GetRemoteAddressUseCase
import com.fappslab.viacep.form.domain.usecase.SetLocalAddressUseCase
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

@ExperimentalCoroutinesApi
internal class FormViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val initialState = FormViewState()
    private val getRemoteAddressUseCase: GetRemoteAddressUseCase = mockk()
    private val setLocalAddressUseCase: SetLocalAddressUseCase = mockk()
    private lateinit var subject: FormViewModel

    @Before
    fun setUp() {
        subject = FormViewModel(
            getRemoteAddressUseCase = getRemoteAddressUseCase,
            setLocalAddressUseCase = setLocalAddressUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getRemoteAddressSuccess Should expose state When onRequestAddress return success response`() {
        // Given
        val address = addressResponse.toAddress()
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(
            zipcode = address.zipcode,
            street = address.street,
            district = address.district,
            city = address.city,
            state = address.state,
            areaCode = address.areaCode
        )
        coEvery { getRemoteAddressUseCase(any()) } returns address

        // When
        subject.onRequestAddress(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
        coVerify { getRemoteAddressUseCase(any()) }
    }

    @Test
    fun `getRemoteAddressFailure Should expose action Error When onRequestAddress return failure response`() {
        // Given
        val throwable = Throwable("Error message")
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(
            shouldShowError = true,
            errorMessage = throwable.message
        )
        coEvery { getRemoteAddressUseCase(any()) } throws throwable

        // When
        subject.onRequestAddress(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
        coVerify { getRemoteAddressUseCase(any()) }
    }

    @Test
    fun `finishView Should expose FinishView Action When invoke onFinishView`() {
        // Given
        val expectedAction = FormViewAction.FinishView

        // When
        subject.onFinishView()

        // Then
        runTest {
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `saveLocalAddressSuccess Should expose ClearForm Action When invoke onSaveAddress`() {
        // Given
        val address = addressResponse.toAddress()
        val expectedAction = FormViewAction.ClearForm
        coEvery { getRemoteAddressUseCase(any()) } returns address
        coEvery { setLocalAddressUseCase(any()) } returns Unit
        runTest { subject.onRequestAddress(zipcode = "01001-000") }

        // When
        subject.onSaveLocalAddress()

        // Then
        runTest {
            subject.action.test {
                assertEquals(expectedAction, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
        coVerify { getRemoteAddressUseCase(any()) }
    }

    @Test
    fun `textChangedZipcode Should expose state When invoke onTextChangedZipcode`() {
        // Given
        val expectedState = initialState.copy(zipcode = "01001-000")

        // When
        subject.onTextChangedZipcode(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedStreet Should expose state When invoke onTextChangedStreet`() {
        // Given
        val expectedState = initialState.copy(street = "Praça da Sé")

        // When
        subject.onTextChangedStreet(street = "Praça da Sé")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedDistrict Should expose state When invoke onTextChangedDistrict`() {
        // Given
        val expectedState = initialState.copy(district = "Sé")

        // When
        subject.onTextChangedDistrict(district = "Sé")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedCity Should expose state When invoke onTextChangedCity`() {
        // Given
        val expectedState = initialState.copy(city = "São Paulo")

        // When
        subject.onTextChangedCity(city = "São Paulo")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedState Should expose state When invoke onTextChangedState`() {
        // Given
        val expectedState = initialState.copy(state = "SP")

        // When
        subject.onTextChangedState(state = "SP")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `textChangedAreaCode Should expose state When invoke onTextChangedAreaCode`() {
        // Given
        val expectedState = initialState.copy(areaCode = "AreaCode")

        // When
        subject.onTextChangedAreaCode(areaCode = "AreaCode")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `closeError Should expose state When invoke onCloseError`() {
        // Given
        val expectedState = initialState.copy(shouldShowError = false, errorMessage = null)

        // When
        subject.onCloseError()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
