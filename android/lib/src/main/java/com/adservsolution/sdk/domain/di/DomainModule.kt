package com.adservsolution.sdk.domain.di

import com.adservsolution.sdk.domain.usecase.AddUserUseCase
import com.adservsolution.sdk.domain.usecase.GetUserUseCase
import com.adservsolution.sdk.domain.usecase.SendEventUseCase
import org.koin.dsl.module

val domainModule = module {
    // Use Cases
    factory {
        AddUserUseCase(get())
    }

    factory {
        GetUserUseCase(get())
    }

    factory {
        SendEventUseCase(get())
    }
}
