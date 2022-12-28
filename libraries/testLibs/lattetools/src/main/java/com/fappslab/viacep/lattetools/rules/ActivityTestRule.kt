package com.fappslab.viacep.lattetools.rules

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.Description
import kotlin.reflect.KClass

abstract class ActivityTestRule<A : Activity>(
    private val activityKClass: KClass<A>,
    private val activityIntent: Intent? = null,
) : KoinTestRule() {

    private lateinit var activityScenario: ActivityScenario<A>

    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestRule()
    }

    protected fun launchActivity(activity: (A) -> Unit) {
        activityScenario = createScenario().onActivity(activity)
    }

    private fun createScenario(): ActivityScenario<A> {
        val intent = activityIntent ?: Intent(
            ApplicationProvider.getApplicationContext(),
            activityKClass.java
        )

        return ActivityScenario.launch<A>(intent).apply {
            moveToState(Lifecycle.State.RESUMED)
        }
    }

    private fun closeActivityScenario() {
        activityScenario.close()
    }

    private fun cleanupTestRule() {
        closeActivityScenario()
    }
}
