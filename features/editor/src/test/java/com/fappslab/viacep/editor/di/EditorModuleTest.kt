package com.fappslab.viacep.editor.di

import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.lattetools.koin.KoinModuleTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class EditorModuleTest : KoinModuleTest(EditorModule) {

    @get:Rule
    override val dispatcherRule = DispatcherTestRule()

    @Test
    fun `checkModules Should Koin provides dependencies When invoke EditorModule`() {
        startKoinTest()
    }
}
