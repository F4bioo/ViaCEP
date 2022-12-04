package com.fappslab.viacep.form.di

import com.fappslab.viacep.arch.koin.KoinLoad
import com.fappslab.viacep.form.data.repository.FormRepositoryImpl
import com.fappslab.viacep.form.data.service.FormService
import com.fappslab.viacep.form.data.source.FormDataSourceImpl
import com.fappslab.viacep.form.domain.usecase.GetRemoteAddressUseCase
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import com.fappslab.viacep.remote.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

object FormModule : KoinLoad() {

    override val presentation = module {
        viewModel {
            FormViewModel(
                getRemoteAddressUseCase = getRemoteAddressUseCase(),
            )
        }
    }

    private fun Scope.getRemoteAddressUseCase() =
        GetRemoteAddressUseCase(
            repository = getFormRepository()
        )

    private fun Scope.getFormRepository() =
        FormRepositoryImpl(
            dataSource = FormDataSourceImpl(
                service = get<HttpClient>().create(
                    clazz = FormService::class.java
                )
            )
        )
}
