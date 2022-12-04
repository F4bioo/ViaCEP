package com.fappslab.viacep.lattetools.observable

import androidx.annotation.VisibleForTesting
import com.fappslab.viacep.arch.viewmodel.ViewAction
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.arch.viewmodel.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@ExperimentalCoroutinesApi
fun <S : ViewState, A : ViewAction> ViewModel<S, A>.onActionTest(
    onEachBlock: (A) -> Unit
): Job = action.onEach {
    onEachBlock(it)
}.launchIn(scope = TestScope(UnconfinedTestDispatcher()))
