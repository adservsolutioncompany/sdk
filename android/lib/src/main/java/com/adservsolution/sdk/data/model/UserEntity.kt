package com.adservsolution.sdk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adservsolution.sdk.domain.model.User

@Entity(
    tableName = "users"
)
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val email: String? = null,
    val fullName: String? = null
) {

    companion object {

        fun toEntity(user: User) = UserEntity(
            userId = user.userId,
            email = user.email,
            fullName = user.fullName
        )

        fun toDomain(userEntity: UserEntity?): User? {
            if (userEntity == null) {
                return null
            }

            return User(
                userId = userEntity.userId,
                email = userEntity.email,
                fullName = userEntity.fullName
            )
        }
    }
}
