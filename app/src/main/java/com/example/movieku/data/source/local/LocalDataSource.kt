package com.example.movieku.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.data.source.local.room.MoviekuDao

class LocalDataSource private constructor(private val moviekuDao: MoviekuDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(moviekuDao: MoviekuDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(moviekuDao)
    }

    fun getUpcomingMovies(): DataSource.Factory<Int, UpcomingMovieEntity> =
        moviekuDao.getUpcomingMovies()

    fun getPopularMovies(): DataSource.Factory<Int, PopularMovieEntity> =
        moviekuDao.getPopularMovies()

    fun getUpBookmarkedMovies(): DataSource.Factory<Int, UpcomingMovieEntity> =
        moviekuDao.getUpBookmarkedMovies()

    fun getPopBookmarkedMovies(): DataSource.Factory<Int, PopularMovieEntity> =
        moviekuDao.getPopBookmarkedMovies()

    fun getUpMovieDetail(movieId: Int): LiveData<UpcomingMovieEntity> =
        moviekuDao.getUpMovieDetail(movieId)

    fun getPopMovieDetail(movieId: Int): LiveData<PopularMovieEntity> =
        moviekuDao.getPopMovieDetail(movieId)

    fun insertUpMovie(upcomingMovieEntity: List<UpcomingMovieEntity>) =
        moviekuDao.insertUpMovie(upcomingMovieEntity)

    fun insertPopMovie(popularMovieEntity: List<PopularMovieEntity>) =
        moviekuDao.insertPopMovie(popularMovieEntity)

    fun insertUpMovieById(upcomingMovieEntity: UpcomingMovieEntity) =
        moviekuDao.insertUpMovieById(upcomingMovieEntity)

    fun insertPopMovieById(popularMovieEntity: PopularMovieEntity) =
        moviekuDao.insertPopMovieById(popularMovieEntity)

    fun setUpMovieBookmark(upcomingMovieEntity: UpcomingMovieEntity, newState: Boolean) {
        upcomingMovieEntity.bookmarked = newState
        moviekuDao.updateUpMovie(upcomingMovieEntity)
    }

    fun setPopMovieBookmark(popularMovieEntity: PopularMovieEntity, newState: Boolean) {
        popularMovieEntity.bookmarked = newState
        moviekuDao.updatePopMovie(popularMovieEntity)
    }
}