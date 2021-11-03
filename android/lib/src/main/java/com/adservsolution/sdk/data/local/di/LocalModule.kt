package com.adservsolution.sdk.data.local.di

import com.adservsolution.sdk.data.SdkDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single {
        SdkDatabase.buildDatabase(androidContext())
    }

    factory {
        get<SdkDatabase>().userDao()
    }
}
