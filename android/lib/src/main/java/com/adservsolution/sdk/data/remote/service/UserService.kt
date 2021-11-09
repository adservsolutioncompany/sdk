package com.adservsolution.sdk.data.remote.service

import com.adservsolution.sdk.domain.model.request.AddUserRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("/appmc")
    fun addUserAsync(
        @Body request: AddUserRequest,
        @Query("token") advToken: String
    ): Deferred<Response<String>>
}
