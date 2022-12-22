package com.fappslab.viacep.lattetools.robot

interface CheckRobot<CB : CheckRobot<CB>> {
    @Suppress("UNCHECKED_CAST")
    fun thenCheck(block: CB.() -> Unit): CB {
        block(this as CB)
        return this
    }
}
