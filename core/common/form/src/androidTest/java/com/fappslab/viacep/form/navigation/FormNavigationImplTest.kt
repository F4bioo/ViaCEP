package com.fappslab.viacep.form.navigation

import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.viacep.design.R
import com.fappslab.viacep.form.presentation.FormFragment
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import kotlin.test.assertIs

@RunWith(AndroidJUnit4::class)
internal class FormNavigationImplTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(setupModules()) }

    private fun setupModules() = module {
        viewModel {
            mockk<FormViewModel>(relaxed = true) {
                every { state } returns MutableStateFlow(FormViewState())
            }
        }
    }

    @Test
    fun whenInvokeNewInstance_shouldOpenFormFragment() {
        // Given
        val subject = FormNavigationImpl()

        // When
        val scenario = launchFragment(themeResId = R.style.Theme_Ds) {
            subject.newInstance()
        }

        // Then
        scenario.onFragment { fragment ->
            assertIs<FormFragment>(fragment)
        }
    }
}
