package com.fappslab.viacep.lattetools.livedata.extension

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.fappslab.viacep.arch.viewmodel.ViewAction
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.arch.viewmodel.ViewState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun <S : ViewState, A : ViewAction> ViewModel<S, A>.attachObservers(
    stateObserver: Observer<S>,
    actionObserver: Observer<A>
) {
    state.attachObserver(stateObserver)
    action.attachObserver(actionObserver)
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun <S : ViewState, A : ViewAction> ViewModel<S, A>.detachObservers(
    stateObserver: Observer<S>,
    actionObserver: Observer<A>
) {
    state.detachObserver(stateObserver)
    action.detachObserver(actionObserver)
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun <S : ViewState> StateFlow<S>.attachObserver(stateObserver: Observer<S>) =
    asLiveData().observeForever(stateObserver)

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun <A : ViewAction> SharedFlow<A>.attachObserver(actionObserver: Observer<A>) =
    asLiveData().observeForever(actionObserver)

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun <S : ViewState> StateFlow<S>.detachObserver(stateObserver: Observer<S>) =
    asLiveData().removeObserver(stateObserver)

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun <A : ViewAction> SharedFlow<A>.detachObserver(actionObserver: Observer<A>) =
    asLiveData().removeObserver(actionObserver)
