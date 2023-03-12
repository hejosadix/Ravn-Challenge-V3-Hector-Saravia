package com.gmail.hejosadix.starwars.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.hejosadix.starwars.data.local.dao.FavoriteDao
import com.gmail.hejosadix.starwars.data.local.entity.Favorite


@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [FavoriteDataConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "star-wars.db")
                .build()


    }
}
