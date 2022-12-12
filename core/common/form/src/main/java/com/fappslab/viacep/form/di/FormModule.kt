package com.fappslab.viacep.form.di

import com.fappslab.viacep.arch.koin.KoinLoad
import com.fappslab.viacep.form.data.repository.FormRepositoryImpl
import com.fappslab.viacep.form.data.service.FormService
import com.fappslab.viacep.form.data.source.local.FormLocalDataSourceImpl
import com.fappslab.viacep.form.data.source.remote.FormRemoteDataSourceImpl
import com.fappslab.viacep.form.domain.usecase.GetLocalAddressUseCase
import com.fappslab.viacep.form.domain.usecase.GetRemoteAddressUseCase
import com.fappslab.viacep.form.domain.usecase.SetLocalAddressUseCase
import com.fappslab.viacep.form.navigation.FormNavigationImpl
import com.fappslab.viacep.form.presentation.viewmodel.FormViewModel
import com.fappslab.viacep.local.client.Database
import com.fappslab.viacep.local.database.FormDatabase
import com.fappslab.viacep.navigation.FormNavigation
import com.fappslab.viacep.remote.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

object FormModule : KoinLoad() {

    override val presentation = module {
        viewModel {
            FormViewModel(
                getRemoteAddressUseCase = getRemoteAddressUseCase(),
                getLocalAddressUseCase = getLocalAddressUseCase(),
                setLocalAddressUseCase = setLocalAddressUseCase()
            )
        }
    }

    override val additionalModule = module {
        factory<FormNavigation> { FormNavigationImpl() }
    }

    private fun Scope.getRemoteAddressUseCase() =
        GetRemoteAddressUseCase(repository = getFormRepository())

    private fun Scope.getLocalAddressUseCase() =
        GetLocalAddressUseCase(repository = getFormRepository())

    private fun Scope.setLocalAddressUseCase() =
        SetLocalAddressUseCase(repository = getFormRepository())

    private fun Scope.getFormRepository() =
        FormRepositoryImpl(
            remoteDataSource = FormRemoteDataSourceImpl(
                service = get<HttpClient>().create(
                    clazz = FormService::class.java
                )
            ),
            localDataSource = FormLocalDataSourceImpl(
                database = get<Database<FormDatabase>>().create()
            )
        )
}
