package com.fappslab.viacep.arch.rules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class LocalTestRule(
    private val ruleChain: RuleChain = RuleChain.emptyRuleChain()
) : TestRule, KoinTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private var database: RoomDatabase? = null

    override fun apply(base: Statement, description: Description): Statement =
        ruleChain
            .around(SetupKoinRule())
            .apply(base, description)

    fun loadKoinModules(block: MutableList<Module>.() -> Unit) {
        mutableListOf<Module>()
            .apply(block)
            .run { loadKoinModules(this) }
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified Database : RoomDatabase> createTestDatabase(): Database {
        database = Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            Database::class.java
        ).allowMainThreadQueries().build()

        return database as Database
    }

    private inner class SetupKoinRule : ExternalResource() {

        override fun before() {
            super.before()
            stopKoin()
            startKoin {
                androidContext(context)
            }
        }

        override fun after() {
            super.after()
            database?.close()
            stopKoin()
        }
    }
}
