package com.adservsolution.sdk.domain.usecase

import com.adservsolution.sdk.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {

    operator fun invoke() = repository.getUser()
}
