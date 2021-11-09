package com.adservsolution.sdk.domain.usecase

import com.adservsolution.sdk.domain.model.request.AddUserRequest
import com.adservsolution.sdk.domain.repository.UserRepository

class AddUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        request: AddUserRequest,
        advToken: String
    ) = repository.addUser(
        request,
        advToken
    )
}
