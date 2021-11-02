package com.adservsolution.sdk.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adservsolution.sdk.data.local.dao.UserDao
import com.adservsolution.sdk.data.model.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SdkDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SdkDatabase::class.java,
                context.packageName
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
