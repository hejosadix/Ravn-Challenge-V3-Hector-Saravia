package com.gmail.hejosadix.starwars.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDAO<T> {
    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insert(
        model: T,
    ): Long

    @Delete
    fun delete(
        model: T,
    )
}
