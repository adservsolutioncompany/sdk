package com.adservsolution.sdk.data.remote.di

import com.adservsolution.sdk.data.remote.datasource.EventDatasource
import com.adservsolution.sdk.data.remote.datasource.UserDatasource
import com.adservsolution.sdk.data.remote.service.EventService
import com.adservsolution.sdk.data.remote.service.UserService
import com.adservsolution.sdk.data.remote.NetworkConnectionInterceptor
import com.adservsolution.sdk.data.remote.NullOnEmptyConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 60L

fun createRemoteModule(baseUrl: String) = module {

    factory {
        GsonBuilder()
            .setLenient()
            .create()
    }

    factory {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory {
        NetworkConnectionInterceptor(androidContext())
    }

    factory {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get() as HttpLoggingInterceptor)
            .addInterceptor(get() as NetworkConnectionInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(get() as Gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    // Services
    factory {
        get<Retrofit>().create(UserService::class.java)
    }

    factory {
        get<Retrofit>().create(EventService::class.java)
    }

    // Datasources
    factory {
        UserDatasource(get())
    }

    factory {
        EventDatasource(get())
    }
}
