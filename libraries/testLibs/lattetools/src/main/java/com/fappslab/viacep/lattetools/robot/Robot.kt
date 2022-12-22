package com.fappslab.viacep.lattetools.robot

import com.fappslab.viacep.arch.viewmodel.Action
import com.fappslab.viacep.arch.viewmodel.State
import com.fappslab.viacep.arch.viewmodel.ViewModel

interface Robot<CB : CheckRobot<CB>, S : State, A : Action, VM : ViewModel<S, A>> {
    fun givenState(state: () -> S): Robot<CB, S, A, VM> = this
    fun givenAction(invoke: VM.() -> Unit, action: () -> A): Robot<CB, S, A, VM> = this
    fun whenLaunch(): CB
}
