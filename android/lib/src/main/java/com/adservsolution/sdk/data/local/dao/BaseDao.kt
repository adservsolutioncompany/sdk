package com.adservsolution.sdk.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(data: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(data: List<T>): List<Long>
}
