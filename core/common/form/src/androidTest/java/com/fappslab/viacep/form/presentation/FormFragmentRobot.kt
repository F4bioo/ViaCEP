package com.fappslab.viacep.form.presentation

import android.os.Bundle
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.design.R
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

private typealias RobotAlias =
        Robot<FormFragmentCheckRobot, FormViewState, FormViewAction, FormViewModel>

@ExperimentalCoroutinesApi
internal class FormFragmentRobot(
    bundle: Bundle? = null,
) : FragmentTestRule<FormFragment>(
    fragmentKClass = FormFragment::class,
    themeResId = R.style.Theme_Ds,
    fragmentArgs = bundle
), RobotAlias {

    @get:Rule
    val dispatcherRule = DispatcherTestRule(testDispatcher = UnconfinedTestDispatcher())

    private val fakeState = MutableStateFlow(FormViewState())
    private val fakeAction = MutableSharedFlow<FormViewAction>()
    private val fakeViewModel = mockk<FormViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    override fun setupModules() = module {
        viewModel { fakeViewModel }
    }

    override fun givenState(
        state: () -> FormViewState,
    ): RobotAlias {
        fakeState.update { state() }
        return this
    }

    override fun givenAction(
        invoke: FormViewModel.() -> Unit,
        action: () -> FormViewAction,
    ): RobotAlias {
        every {
            invoke(fakeViewModel)
        } coAnswers {
            fakeAction.emit(action())
        }
        return this
    }

    override fun whenLaunch(): FormFragmentCheckRobot {
        launchFragment()
        return FormFragmentCheckRobot()
    }
}
