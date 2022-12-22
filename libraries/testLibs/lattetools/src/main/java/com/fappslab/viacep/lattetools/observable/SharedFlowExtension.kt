package com.fappslab.viacep.lattetools.observable

import androidx.annotation.VisibleForTesting
import com.fappslab.viacep.arch.viewmodel.Action
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.arch.viewmodel.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@ExperimentalCoroutinesApi
suspend fun <S : State, A : Action> ViewModel<S, A>.actionTests(
    actionBlock: suspend (A?) -> Unit
) {
    actionBlock(action.firstOrNull())
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@ExperimentalCoroutinesApi
suspend fun <S : State, A : Action> actionTest(
    viewModel: ViewModel<S, A>,
    actionBlock: suspend (A?) -> Unit
) {
    actionBlock(viewModel.action.firstOrNull())
}
