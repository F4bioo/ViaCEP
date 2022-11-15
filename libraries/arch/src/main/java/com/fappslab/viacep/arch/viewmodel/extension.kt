package com.fappslab.viacep.arch.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

inline fun <reified S : ViewState, reified A : ViewAction> LifecycleOwner.onViewState(
    viewModel: ViewModel<S, A>,
    crossinline state: (S) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect { eventState ->
                state(eventState)
            }
        }
    }
}

inline fun <reified S : ViewState, reified A : ViewAction> LifecycleOwner.onViewAction(
    viewModel: ViewModel<S, A>,
    crossinline action: (A) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.action.collect { eventAction ->
                action(eventAction)
            }
        }
    }
}
