package com.fappslab.viacep

import android.app.Application
import com.fappslab.viacep.di.MainModule
import com.fappslab.viacep.remote.di.DataRemoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration

object KoinAppDeclaration {

    fun get(application: Application): KoinAppDeclaration = {
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        androidContext(application)

        // Koin Load
        DataRemoteModule.load()
        MainModule.load()
    }
}
