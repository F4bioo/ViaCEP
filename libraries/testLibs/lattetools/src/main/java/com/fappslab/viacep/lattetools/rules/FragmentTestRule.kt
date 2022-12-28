package com.fappslab.viacep.lattetools.rules

import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import com.google.android.material.R
import org.junit.runner.Description
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

@VisibleForTesting(otherwise = Modifier.PRIVATE)
abstract class FragmentTestRule<F : Fragment>(
    private val fragmentKClass: KClass<F>,
    private val fragmentArgs: Bundle? = null,
    @StyleRes private val themeResId: Int = R.style.Theme_MaterialComponents_Light_DarkActionBar,
    private val lifecycleInitState: Lifecycle.State = Lifecycle.State.RESUMED,
) : KoinTestRule() {

    private lateinit var fragmentScenario: FragmentScenario<F>

    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestRule()
    }

    protected fun launchFragment(fragment: (F) -> Unit) {
        fragmentScenario = FragmentScenario.launchInContainer(
            fragmentKClass.java,
            fragmentArgs,
            themeResId,
            lifecycleInitState
        ).apply {
            stateScenarioHandler(scenario = this)
        }.onFragment(fragment)
    }

    private fun stateScenarioHandler(scenario: FragmentScenario<F>) {
        if (lifecycleInitState != Lifecycle.State.RESUMED) {
            scenario.moveToState(Lifecycle.State.RESUMED)
        }
    }

    private fun closeFragmentScenario() {
        fragmentScenario.apply {
            moveToState(Lifecycle.State.DESTROYED)
            close()
        }
    }

    private fun cleanupTestRule() {
        closeFragmentScenario()
    }
}
