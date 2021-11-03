package com.adservsolution.sdk.data.remote.service

import com.adservsolution.sdk.domain.model.request.AddUserRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/appmc")
    fun addUserAsync(
        @Body request: AddUserRequest
    ): Deferred<Response<String>>
}
