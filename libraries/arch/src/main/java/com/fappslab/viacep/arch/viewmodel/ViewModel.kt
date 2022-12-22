package com.fappslab.viacep.arch.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel as ViewModelFromAndroidX

abstract class ViewModel<S : State, A : Action>(
    initialState: S
) : ViewModelFromAndroidX() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _action = MutableSharedFlow<A>()
    val action: Flow<A> = _action.asSharedFlow()

    protected fun onState(stateBlock: (S) -> S) {
        _state.update { stateBlock(it) }
    }

    protected fun onAction(actionBlock: () -> A) {
        viewModelScope.launch {
            _action.emit(actionBlock())
        }
    }
}
