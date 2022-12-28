package com.fappslab.viacep.lattetools.observable

import androidx.annotation.VisibleForTesting
import com.fappslab.viacep.arch.viewmodel.Action
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.arch.viewmodel.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@ExperimentalCoroutinesApi
suspend fun <S : State, A : Action> TestScope.stateTest(
    viewModel: ViewModel<S, A>,
    stateBlock: suspend (S) -> Unit
) {
    runCurrent()
    stateBlock(viewModel.state.value)
}
