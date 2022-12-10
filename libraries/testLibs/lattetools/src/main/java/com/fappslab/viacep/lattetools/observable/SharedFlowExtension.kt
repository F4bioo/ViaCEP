package com.fappslab.viacep.lattetools.observable

import androidx.annotation.VisibleForTesting
import com.fappslab.viacep.arch.viewmodel.ViewAction
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.arch.viewmodel.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@ExperimentalCoroutinesApi
suspend fun <S : ViewState, A : ViewAction> ViewModel<S, A>.actionTests(
    actionBlock: suspend (A?) -> Unit
) {
    actionBlock(action.firstOrNull())
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@ExperimentalCoroutinesApi
suspend fun <S : ViewState, A : ViewAction> actionTest(
    viewModel: ViewModel<S, A>,
    actionBlock: suspend (A?) -> Unit
) {
    actionBlock(viewModel.action.firstOrNull())
}
