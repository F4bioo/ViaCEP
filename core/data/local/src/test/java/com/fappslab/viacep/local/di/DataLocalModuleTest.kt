package com.fappslab.viacep.local.di

import android.content.Context
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.lattetools.koin.KoinModuleTest
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal class DataLocalModuleTest : KoinModuleTest(DataLocalModule) {

    @get:Rule
    override val dispatcherRule = DispatcherTestRule()

    override val mockedModules = module {
        factory<Context> { mockk() }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke DataLocalModule`() {
        startKoinTest()
    }
}
