package com.fappslab.viacep.lattetools.robot

interface CheckRobot<T : CheckRobot<T>> {
    @Suppress("UNCHECKED_CAST")
    fun thenCheck(block: T.() -> Unit) {
        block(this as T)
    }
}
