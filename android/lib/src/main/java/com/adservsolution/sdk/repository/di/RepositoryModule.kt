package com.adservsolution.sdk.repository.di

import com.adservsolution.sdk.domain.repository.EventRepository
import com.adservsolution.sdk.domain.repository.UserRepository
import com.adservsolution.sdk.repository.EventRepositoryImpl
import com.adservsolution.sdk.repository.UserRepositoryImpl
import com.adservsolution.sdk.utils.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        AppDispatchers(
            Dispatchers.Main,
            Dispatchers.IO
        )
    }

    factory {
        UserRepositoryImpl(
            get(),
            get()
        ) as UserRepository
    }

    factory {
        EventRepositoryImpl(
            get()
        ) as EventRepository
    }
}
