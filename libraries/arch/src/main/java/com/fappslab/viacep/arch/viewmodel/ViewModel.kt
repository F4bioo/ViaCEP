package com.fappslab.viacep.arch.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as ViewModelFromAndroidX

abstract class ViewModel<S : ViewState, A : ViewAction>(
    initialState: S
) : ViewModelFromAndroidX() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    private val _action = MutableSharedFlow<A>()
    val action: SharedFlow<A> = _action

    fun setState(stateBlock: (S) -> S) {
        _state.value = stateBlock(state.value)
    }

    fun sendAction(actionBlock: () -> A) {
        _action.tryEmit(actionBlock())
    }
}
