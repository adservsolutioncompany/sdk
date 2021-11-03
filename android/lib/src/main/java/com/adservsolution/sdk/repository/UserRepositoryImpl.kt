package com.adservsolution.sdk.repository

import com.adservsolution.sdk.data.local.dao.UserDao
import com.adservsolution.sdk.data.model.UserEntity
import com.adservsolution.sdk.data.remote.datasource.UserDatasource
import com.adservsolution.sdk.domain.model.User
import com.adservsolution.sdk.domain.model.request.AddUserRequest
import com.adservsolution.sdk.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDatasource: UserDatasource,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun addUser(
        request: AddUserRequest
    ): User? {
        val response = safeApiCall {
            userDatasource.addUserAsync(request).await()
        }

        response?.let {
            val user = User(userId = it)
            userDao.save(UserEntity.toEntity(user))
            return user
        }

        return null
    }

    override fun getUser(): Flow<User?> =
        userDao.getUser().map { UserEntity.toDomain(it) }
}
