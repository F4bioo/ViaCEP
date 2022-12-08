package com.fappslab.viacep.form.di

import android.content.Context
import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.lattetools.koin.KoinModuleTest
import com.fappslab.viacep.local.client.Database
import com.fappslab.viacep.local.client.DatabaseImpl
import com.fappslab.viacep.local.database.FormDatabase
import com.fappslab.viacep.remote.networkmockprovider.provideNetworkModule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal class FormModuleTest : KoinModuleTest(FormModule) {

    @get:Rule
    override val dispatcherRule = DispatcherTestRule()

    override val networkModules = provideNetworkModule(
        httpUrl = "https://viacep.com.br".toHttpUrl()
    )

    override val mockedModules = module {
        factory<Context> { mockk() }
        factory<Database<FormDatabase>> { DatabaseImpl(database = mockk()) }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke FormModule`() {
        startKoinTest()
    }
}
