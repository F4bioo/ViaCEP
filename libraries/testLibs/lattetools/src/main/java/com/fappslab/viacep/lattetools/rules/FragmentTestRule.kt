package com.fappslab.viacep.lattetools.rules

import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import com.google.android.material.R
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

@VisibleForTesting(otherwise = Modifier.PRIVATE)
abstract class FragmentTestRule<F : Fragment>(
    private val fragmentKClass: KClass<F>,
    private val fragmentArgs: Bundle? = null,
    @StyleRes private val themeResId: Int = R.style.Theme_MaterialComponents_Light_DarkActionBar,
    private val lifecycleInitState: Lifecycle.State = Lifecycle.State.RESUMED
) : TestRule, KoinTest {

    private lateinit var fragmentScenario: FragmentScenario<F>

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
        destroyFragment()
    }

    protected fun launchFragment(): FragmentScenario<F> {
        fragmentScenario = FragmentScenario.launchInContainer(
            fragmentKClass.java,
            fragmentArgs,
            themeResId,
            lifecycleInitState
        )
        return fragmentScenario
    }

    fun runWithFragmentContext(fragmentBlock: (Fragment) -> Unit) {
        launchFragment()
        fragmentScenario.onFragment(fragmentBlock)
        finished()
    }

    private fun destroyFragment() {
        fragmentScenario.moveToState(Lifecycle.State.DESTROYED)
    }
}
