package com.fappslab.viacep.form.presentation

import android.os.Bundle
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.arch.viewmodel.ViewAction
import com.fappslab.viacep.arch.viewmodel.ViewState
import com.fappslab.viacep.form.presentation.viewmodel.FormViewAction
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import com.fappslab.viacep.form.presentation.viewmodel.FormViewState
import com.fappslab.viacep.lattetools.robot.Robot
import com.fappslab.viacep.lattetools.rules.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal class FormFragmentRobot(
    bundle: Bundle? = null
) : FragmentTestRule<FormFragment>(FormFragment::class, bundle), Robot<FormFragmentCheckRobot> {

    @get:Rule
    val dispatcherRule = DispatcherTestRule(testDispatcher = UnconfinedTestDispatcher())

    private val initialState = FormViewState()
    private val fakeState = MutableStateFlow(initialState)
    private val fakeAction = MutableSharedFlow<FormViewAction>()

    override fun setupModules() = module {
        viewModel {
            mockk<FormViewModel>(relaxed = true) {
                every { state } returns fakeState
                every { action } returns fakeAction
            }
        }
    }

    override fun givenState(state: () -> ViewState): Robot<FormFragmentCheckRobot> {
        fakeState.update { state() as FormViewState }
        return super.givenState(state)
    }

    override fun givenAction(action: () -> ViewAction): Robot<FormFragmentCheckRobot> {
        fakeAction.tryEmit(action() as FormViewAction)
        return super.givenAction(action)
    }

    override fun whenExecute(): FormFragmentCheckRobot {
        launchFragment()
        return FormFragmentCheckRobot()
    }
}
