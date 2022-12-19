package com.fappslab.viacep.lattetools.robot

import com.fappslab.viacep.arch.viewmodel.ViewAction
import com.fappslab.viacep.arch.viewmodel.ViewState

interface Robot<T : CheckRobot<T>> {
    fun givenState(state: () -> ViewState): Robot<T> = this
    fun givenAction(action: () -> ViewAction): Robot<T> = this
    fun whenExecute(): T
}
