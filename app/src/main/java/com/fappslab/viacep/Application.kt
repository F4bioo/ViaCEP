package com.fappslab.viacep

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import timber.log.Timber

open class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin()
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

    open fun startKoin() {
        startKoin(appDeclaration = KoinAppDeclaration.get(this))
    }
}
