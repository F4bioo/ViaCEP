package com.fappslab.viacep.local.di

import com.fappslab.viacep.arch.koin.KoinLoad
import com.fappslab.viacep.local.BuildConfig
import com.fappslab.viacep.local.client.Database
import com.fappslab.viacep.local.client.DatabaseImpl
import com.fappslab.viacep.local.client.RoomDatabaseBuilder
import com.fappslab.viacep.local.database.FormDatabase
import org.koin.dsl.module

object DataLocalModule : KoinLoad() {

    override val dataModule = module {
        single<RoomDatabaseBuilder<FormDatabase>> {
            RoomDatabaseBuilder(
                context = get(),
                databaseName = BuildConfig.DB_NAME,
                databaseClass = FormDatabase::class.java
            )
        }

        single<Database<FormDatabase>> {
            DatabaseImpl(
                database = get<RoomDatabaseBuilder<FormDatabase>>().build()
            )
        }
    }
}
