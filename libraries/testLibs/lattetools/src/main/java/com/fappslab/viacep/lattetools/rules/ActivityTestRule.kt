package com.fappslab.viacep.lattetools.rules

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import kotlin.reflect.KClass

abstract class ActivityTestRule<A : Activity>(
    private val activityKClass: KClass<A>,
    private val activityIntent: Intent? = null
) : TestRule, KoinTest {

    private lateinit var activityScenario: ActivityScenario<A>

    abstract fun setupModules(): Module

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                starting()
                try {
                    base.evaluate()
                } finally {
                    finished()
                }
            }
        }
    }

    private fun starting() {
        stopKoin()
        startKoin { loadKoinModules(setupModules()) }
    }

    private fun finished() {
        Thread.sleep(1000)
        stopKoin()
        destroyActivity()
    }

    protected fun launchActivity(): ActivityScenario<A> {
        activityScenario = ActivityScenario.launch(
            activityIntent?.apply {
                component = ComponentName(
                    ApplicationProvider.getApplicationContext(),
                    activityKClass.java
                )
            }
        )

        return activityScenario
    }

    fun runWithActivityContext(activityBlock: (Activity) -> Unit) {
        launchActivity()
        activityScenario.onActivity(activityBlock)
        finished()
    }

    private fun destroyActivity() {
        activityScenario.close()
    }
}
