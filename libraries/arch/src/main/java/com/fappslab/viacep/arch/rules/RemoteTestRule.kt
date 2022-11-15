package com.fappslab.viacep.arch.rules

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "baseUrl"

open class RemoteTestRule(
    private val ruleChain: RuleChain = RuleChain.emptyRuleChain()
) : TestRule, KoinTest {

    val mockWebServer = MockWebServer()

    override fun apply(base: Statement, description: Description): Statement =
        ruleChain
            .around(mockWebServer)
            .around(SetupKoinRule())
            .apply(base, description)

    fun addMockDispatcher(dispatcher: Dispatcher) {
        mockWebServer.dispatcher = dispatcher
    }

    fun loadKoinModules(block: MutableList<Module>.() -> Unit) {
        mutableListOf<Module>()
            .apply(block)
            .run { loadKoinModules(this) }
    }

    inline fun <reified Service> createTestService(): Service =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(Service::class.java)

    private inner class SetupKoinRule : ExternalResource() {

        override fun before() {
            super.before()
            stopKoin()
            startKoin {
                properties(mapOf(BASE_URL to mockWebServer.url("/").toString()))
            }
        }

        override fun after() {
            super.after()
            mockWebServer.shutdown()
            stopKoin()
        }
    }
}
