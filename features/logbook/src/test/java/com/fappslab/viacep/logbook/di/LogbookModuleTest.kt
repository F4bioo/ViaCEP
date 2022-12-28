package com.fappslab.viacep.logbook.di

import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.form.domain.usecase.DeleteLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressesUseCase
import com.fappslab.viacep.lattetools.koin.KoinModuleTest
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal class LogbookModuleTest : KoinModuleTest(LogbookModule) {

    @get:Rule
    override val dispatcherRule = DispatcherTestRule()

    override val mockedModules = module {
        factory<GetLocalAddressesUseCase> { mockk() }
        factory<DeleteLocalAddressUseCase> { mockk() }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke LogbookModule`() {
        startKoinTest()
    }
}
