package com.adservsolution.sdk.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.adservsolution.sdk.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao : BaseDao<UserEntity>() {

    suspend fun save(userEntity: UserEntity) = insert(userEntity)

    @Query(GET_CURRENT_USER_QUERY)
    abstract fun getUser(): Flow<UserEntity?>

    companion object {

        private const val GET_CURRENT_USER_QUERY = "SELECT * FROM users LIMIT 1;"
    }
}
