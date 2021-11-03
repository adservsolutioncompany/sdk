package com.adservsolution.sdk.data.remote.datasource

import com.adservsolution.sdk.data.remote.service.UserService
import com.adservsolution.sdk.domain.model.request.AddUserRequest

class UserDatasource(
    private val userService: UserService
) {

    fun addUserAsync(request: AddUserRequest) = userService.addUserAsync(request)
}
