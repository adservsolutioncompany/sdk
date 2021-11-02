package com.adservsolution.sdk.data.remote.service

import com.adservsolution.sdk.domain.model.Event
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {

    @GET("/appevent")
    fun sendEventAsync(
        @Query("token") advToken: String,
        @Query("app") appName: String,
        @Query("e") eventId: Event,
        @Query("aud") pixelId: String,
        @Query("user") userId: String
    ): Deferred<Response<Void>>
}
