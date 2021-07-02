package com.example.movieku.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity

@Dao
interface MoviekuDao {

    @Query("SELECT * FROM upcomingmovieentities")
    fun getUpcomingMovies(): DataSource.Factory<Int, UpcomingMovieEntity>

    @Query("SELECT * FROM popularmovieentities")
    fun getPopularMovies(): DataSource.Factory<Int, PopularMovieEntity>

    @Query("SELECT * FROM upcomingmovieentities WHERE bookmarked = 1")
    fun getUpBookmarkedMovies(): DataSource.Factory<Int, UpcomingMovieEntity>

    @Query("SELECT * FROM popularmovieentities WHERE bookmarked = 1")
    fun getPopBookmarkedMovies(): DataSource.Factory<Int, PopularMovieEntity>

    @Transaction
    @Query("SELECT * FROM upcomingmovieentities WHERE id = :id")
    fun getUpMovieDetail(id: Int): LiveData<UpcomingMovieEntity>

    @Transaction
    @Query("SELECT * FROM popularmovieentities WHERE id = :id")
    fun getPopMovieDetail(id: Int): LiveData<PopularMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpMovie(upcomingMovieEntity: List<UpcomingMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopMovie(popularMovieEntity: List<PopularMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpMovieById(upcomingMovieEntity: UpcomingMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopMovieById(popularMovieEntity: PopularMovieEntity)

    @Update
    fun updateUpMovie(upcomingMovieEntity: UpcomingMovieEntity)

    @Update
    fun updatePopMovie(popularMovieEntity: PopularMovieEntity)

}