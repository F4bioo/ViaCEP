package com.fappslab.viacep.lattetools.koin

import com.fappslab.viacep.arch.koin.KoinLoad
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.ParametersBinding
import org.koin.test.check.checkModules

@ExperimentalCoroutinesApi
abstract class KoinModuleTest(
    private val koinLoad: KoinLoad
) : AutoCloseKoinTest() {

    abstract val dispatcherRule: DispatcherTestRule

    protected open val networkModules: Module = module {}

    protected open val mockedModules: Module = module {}

    protected open fun KoinModuleTest.startKoinTest(
        checkBlock: ParametersBinding.() -> Unit = {}
    ): Unit = startKoin {
        modules(koinLoad.modules + networkModules + mockedModules)
    }.checkModules { checkBlock(this) }
}
