package com.fappslab.viacep.arch.koin

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class KoinLoad {

    open val modules: List<Module>
        get() = dataModule + domainModule + presentation + additionalModule

    protected open val dataModule: Module = module { }

    protected open val domainModule: Module = module { }

    protected open val presentation: Module = module { }

    protected open val additionalModule: Module = module { }

    fun load() {
        loadKoinModules(modules)
    }

    fun unload() {
        unloadKoinModules(modules)
    }
}
