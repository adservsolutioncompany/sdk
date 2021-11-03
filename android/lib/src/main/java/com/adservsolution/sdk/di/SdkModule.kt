package com.adservsolution.sdk.di

import android.content.Context
import com.adservsolution.sdk.data.local.di.localModule
import com.adservsolution.sdk.data.remote.di.createRemoteModule
import com.adservsolution.sdk.domain.di.domainModule
import com.adservsolution.sdk.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun startKoin(
    context: Context,
    baseUrl: String
) {
    startKoin {
        printLogger()
        androidContext(context)
        modules(modules(baseUrl))
    }
}

private fun modules(baseUrl: String) = listOf(
    domainModule,
    localModule,
    createRemoteModule(baseUrl),
    repositoryModule
)
