package com.adservsolution.sdk.domain.repository

import com.adservsolution.sdk.domain.model.User
import com.adservsolution.sdk.domain.model.request.AddUserRequest
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun addUser(
        request: AddUserRequest,
        advToken: String
    ): User?

    fun getUser(): Flow<User?>
}
