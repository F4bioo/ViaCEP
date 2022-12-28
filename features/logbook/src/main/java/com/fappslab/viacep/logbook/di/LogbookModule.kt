package com.fappslab.viacep.logbook.di

import com.fappslab.viacep.arch.koin.KoinLoad
import com.fappslab.viacep.logbook.presentation.viewmodel.LogbookViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LogbookModule : KoinLoad() {

    override val presentation = module {
        viewModel {
            LogbookViewModel(
                getLocalAddressesUseCase = get(),
                deleteLocalAddressUseCase = get()
            )
        }
    }
}
