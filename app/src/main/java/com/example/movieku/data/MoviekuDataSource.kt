package com.example.movieku.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.vo.Resource

interface MoviekuDataSource {

    fun getUpcomingMovies(): LiveData<Resource<PagedList<UpcomingMovieEntity>>>

    fun getPopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>>

    fun getUpDetailMovie(movieId: Int): LiveData<Resource<UpcomingMovieEntity>>

    fun getPopDetailMovie(movieId: Int): LiveData<Resource<PopularMovieEntity>>

    fun getUpBookmarkedMovies(): LiveData<PagedList<UpcomingMovieEntity>>

    fun getPopBookmarkedMovies(): LiveData<PagedList<PopularMovieEntity>>

    fun setUpBookmarkedMovie(upcomingMovieEntity: UpcomingMovieEntity, state: Boolean)

    fun setPopBookmarkedMovie(popularMovieEntity: PopularMovieEntity, state: Boolean)
}