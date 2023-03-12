package com.gmail.hejosadix.starwars.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.gmail.hejosadix.starwars.data.local.entity.Favorite
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao : BaseDAO<Favorite> {
    @Query("SELECT * FROM Favorite WHERE id = :id")
    fun favorite(
        id: String,
    ): Flow<Favorite?>

    @Query("DELETE FROM Favorite WHERE id = :id")
    fun deleteById(
        id: String,
    )
}