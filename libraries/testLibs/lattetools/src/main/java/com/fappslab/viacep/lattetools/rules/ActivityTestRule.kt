package com.fappslab.viacep.lattetools.rules

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.Description
import kotlin.reflect.KClass

abstract class ActivityTestRule<A : Activity>(
    private val activityKClass: KClass<A>,
    private val activityIntent: Intent? = null
) : KoinTestRule() {

    private lateinit var activityScenario: ActivityScenario<A>

    override fun finished(description: Description) {
        super.finished(description)
        finished()
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

    private fun finished() {
        Thread.sleep(1000)
        destroyActivity()
    }
}
