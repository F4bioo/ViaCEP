package com.fappslab.viacep.form.di

import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.lattetools.koin.KoinModuleTest
import com.fappslab.viacep.remote.networkmockprovider.provideNetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.HttpUrl
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class FormModuleTest : KoinModuleTest(FormModule) {

    @get:Rule
    override val dispatcherRule = DispatcherTestRule()

    override val networkModules = provideNetworkModule(getHttpUrl())

    @Test
    fun `checkModules Should Koin provides dependencies When invoke FormModule`() {
        startKoinTest()
    }

    private fun getHttpUrl(): HttpUrl =
        HttpUrl.Builder()
            .scheme("https")
            .host("viacep.com.br")
            .build()
}
