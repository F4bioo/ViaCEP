package com.fappslab.viacep.logbook.presentation.viewmodel

import app.cash.turbine.test
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.domain.usecase.DeleteLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressesUseCase
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
internal class LogbookViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val initialState = LogbookViewState()

    private val getLocalAddressesUseCase: GetLocalAddressesUseCase = mockk()
    private val deleteLocalAddressUseCase: DeleteLocalAddressUseCase = mockk()
    private lateinit var subject: LogbookViewModel

    @Before
    fun setUp() {
        subject = LogbookViewModel(
            getLocalAddressesUseCase = getLocalAddressesUseCase,
            deleteLocalAddressUseCase = deleteLocalAddressUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getLocalAddressesSuccess Should expose state When onGetLocalAddresses return success result`() {
        // Given
        val address = addressResponse.toAddress()
        val addresses = listOf(address, address)
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(addresses = addresses)
        coEvery { getLocalAddressesUseCase() } returns addresses

        // When
        subject.onGetLocalAddresses()

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
        coVerify { getLocalAddressesUseCase() }
    }

    @Test
    fun `getLocalAddressesFailure Should expose state When onGetLocalAddresses return failure result`() {
        // Given
        val throwable = Throwable("Error message")
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        coEvery { getLocalAddressesUseCase() } throws throwable

        // When
        subject.onGetLocalAddresses()

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getLocalAddressesUseCase() }
    }

    @Test
    fun `deleteLocalAddressSuccess Should expose state When onDeleteLocalAddress return success result`() {
        // Given
        val address = addressResponse.toAddress()
        val addresses = listOf(address, address)
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(addresses = addresses)
        coEvery { deleteLocalAddressUseCase(any()) } returns Unit
        coEvery { getLocalAddressesUseCase() } returns addresses

        // When
        subject.onDeleteLocalAddress(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { deleteLocalAddressUseCase(any()) }
        coVerify { getLocalAddressesUseCase() }
    }

    @Test
    fun `deleteLocalAddressFailure Should expose state When onDeleteLocalAddress return failure result`() {
        // Given
        val throwable = Throwable("Error message")
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        coEvery { deleteLocalAddressUseCase(any()) } throws throwable

        // When
        subject.onDeleteLocalAddress(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { deleteLocalAddressUseCase(any()) }
    }

    @Test
    fun `cardItem Should expose state true When invoke onCardItem for show editor bottom sheet`() {
        // Given
        val expectedState = initialState.copy(
            shouldShowEditorBottomSheet = true,
            zipcode = "01001-000"
        )

        // When
        subject.onCardItem(zipcode = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `closeEditorBottomSheet Should expose state false When invoke onCloseEditorBottomSheet for dismiss editor bottom sheet`() {
        // Given
        val address = addressResponse.toAddress()
        val addresses = listOf(address, address)
        val expectedFirstState = initialState.copy(shouldShowEditorBottomSheet = false)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = true)
        val expectedTertiaryState = expectedSecondState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedTertiaryState.copy(addresses = addresses)
        coEvery { getLocalAddressesUseCase() } returns addresses

        // When
        subject.onCloseEditorBottomSheet()

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedTertiaryState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getLocalAddressesUseCase() }
    }
}
