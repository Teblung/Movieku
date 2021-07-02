package com.example.movieku.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity

@Database(
    entities = [UpcomingMovieEntity::class, PopularMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviekuDatabase : RoomDatabase() {
    abstract fun moviekuDao(): MoviekuDao

    companion object {
        @Volatile
        private var INSTANCE: MoviekuDatabase? = null

        fun getInstance(context: Context): MoviekuDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MoviekuDatabase::class.java,
                    "Movieku"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}