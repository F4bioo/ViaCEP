package com.fappslab.viacep.remote.networkmockprovider

import androidx.annotation.VisibleForTesting
import com.fappslab.viacep.remote.client.HttpClient
import com.fappslab.viacep.remote.client.HttpClientImpl
import okhttp3.HttpUrl
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun provideNetworkModule(
    httpUrl: HttpUrl
) = networkTestModule(httpUrl)

private fun networkTestModule(
    httpUrl: HttpUrl,
): Module = module {
    factory<Retrofit> {
        Retrofit.Builder()
            .baseUrl(httpUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory<HttpClient> { HttpClientImpl(retrofit = get()) }
}
