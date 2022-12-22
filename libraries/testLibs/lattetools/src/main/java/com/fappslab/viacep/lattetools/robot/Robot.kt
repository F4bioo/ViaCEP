package com.fappslab.viacep.lattetools.robot

import com.fappslab.viacep.arch.viewmodel.ViewAction
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.arch.viewmodel.ViewState

interface Robot<C : CheckRobot<C>, S : ViewState, A : ViewAction, V : ViewModel<S, A>> {
    fun givenState(state: () -> S): Robot<C, S, A, V> = this
    fun givenAction( invoke: V.() -> Unit, action: () -> A ): Robot<C, S, A, V> = this
    fun whenLaunch(): C
}

