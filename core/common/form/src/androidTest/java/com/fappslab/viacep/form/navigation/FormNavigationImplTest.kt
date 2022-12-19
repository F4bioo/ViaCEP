package com.fappslab.viacep.form.navigation

import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.presentation.FormFragment
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import com.fappslab.viacep.lattetools.rules.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
internal class FormNavigationImplTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule(testDispatcher = UnconfinedTestDispatcher())

    @get:Rule
    val formFragmentRobot = object : FragmentTestRule<FormFragment>(FormFragment::class) {
        override fun setupModules() = module {
            viewModel {
                mockk<FormViewModel>(relaxed = true) {
                    every { state } returns fakeState
                }
            }
        }
    }

    private val initialState = FormViewState()
    private val fakeState = MutableStateFlow(initialState)
    private val subject = FormNavigationImpl()

    @Test
    fun whenInvokeNavigationToFeature_shouldOpenFormFragment() {
        // Given
        val expectedFragment = subject.newInstance()

        // Then
        formFragmentRobot.runWithFragmentContext { fragment ->
            assertEquals(expectedFragment::class.simpleName, fragment::class.simpleName)
            assertTrue { fragment.isVisible }
        }
    }
}
