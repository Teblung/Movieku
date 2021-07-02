package com.example.movieku.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieku.data.source.local.LocalDataSource
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.data.source.remote.ApiResponse
import com.example.movieku.data.source.remote.RemoteDataSource
import com.example.movieku.data.source.remote.response.ResultsItem
import com.example.movieku.utils.AppExecutors
import com.example.movieku.vo.Resource

class FakeMoviekuRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MoviekuDataSource {

    override fun getUpcomingMovies(): LiveData<Resource<PagedList<UpcomingMovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<UpcomingMovieEntity>, List<ResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<UpcomingMovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getUpcomingMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<UpcomingMovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllUpcomingMovies()

            override fun saveCallResult(data: List<ResultsItem>) {
                val movieList = ArrayList<UpcomingMovieEntity>()
                for (response in data) {
                    val movie = UpcomingMovieEntity(
                        response.id,
                        response.overview,
                        response.originalLanguage,
                        response.originalTitle,
                        response.video,
                        response.title,
                        response.posterPath,
                        response.popularity,
                        response.voteAverage,
                        response.adult,
                        response.voteCount,
                        response.bookmarked
                    )
                    movieList.add(movie)
                }
                localDataSource.insertUpMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getPopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<PopularMovieEntity>, List<ResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<PopularMovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getPopularMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<PopularMovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllPopularMovies()

            override fun saveCallResult(data: List<ResultsItem>) {
                val movieList = ArrayList<PopularMovieEntity>()
                for (response in data) {
                    val movie = PopularMovieEntity(
                        response.id,
                        response.overview,
                        response.originalLanguage,
                        response.originalTitle,
                        response.video,
                        response.title,
                        response.posterPath,
                        response.popularity,
                        response.voteAverage,
                        response.adult,
                        response.voteCount,
                        response.bookmarked
                    )
                    movieList.add(movie)
                }
                localDataSource.insertPopMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getUpDetailMovie(movieId: Int): LiveData<Resource<UpcomingMovieEntity>> {
        return object :
            NetworkBoundResource<UpcomingMovieEntity, List<ResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<UpcomingMovieEntity> =
                localDataSource.getUpMovieDetail(movieId)

            override fun shouldFetch(data: UpcomingMovieEntity?): Boolean =
                data?.id == null || data.id == 0

            override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllUpcomingMovies()

            override fun saveCallResult(data: List<ResultsItem>) {
                lateinit var movie: UpcomingMovieEntity
                for (response in data) {
                    if (response.id == movieId) {
                        movie = UpcomingMovieEntity(
                            response.id,
                            response.overview,
                            response.originalLanguage,
                            response.originalTitle,
                            response.video,
                            response.title,
                            response.posterPath,
                            response.popularity,
                            response.voteAverage,
                            response.adult,
                            response.voteCount,
                            response.bookmarked
                        )
                    }
                }
                localDataSource.insertUpMovieById(movie)
            }
        }.asLiveData()
    }

    override fun getPopDetailMovie(movieId: Int): LiveData<Resource<PopularMovieEntity>> {
        return object :
            NetworkBoundResource<PopularMovieEntity, List<ResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PopularMovieEntity> =
                localDataSource.getPopMovieDetail(movieId)

            override fun shouldFetch(data: PopularMovieEntity?): Boolean =
                data?.id == null || data.id == 0

            override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllPopularMovies()

            override fun saveCallResult(data: List<ResultsItem>) {
                lateinit var movie: PopularMovieEntity
                for (response in data) {
                    if (response.id == movieId) {
                        movie = PopularMovieEntity(
                            response.id,
                            response.overview,
                            response.originalLanguage,
                            response.originalTitle,
                            response.video,
                            response.title,
                            response.posterPath,
                            response.popularity,
                            response.voteAverage,
                            response.adult,
                            response.voteCount,
                            response.bookmarked
                        )
                    }
                }
                localDataSource.insertPopMovieById(movie)
            }
        }.asLiveData()
    }

    override fun getUpBookmarkedMovies(): LiveData<PagedList<UpcomingMovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getUpBookmarkedMovies(), config).build()
    }

    override fun getPopBookmarkedMovies(): LiveData<PagedList<PopularMovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getPopBookmarkedMovies(), config).build()
    }

    override fun setUpBookmarkedMovie(upcomingMovieEntity: UpcomingMovieEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.setUpMovieBookmark(upcomingMovieEntity, state)
        }

    override fun setPopBookmarkedMovie(popularMovieEntity: PopularMovieEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.setPopMovieBookmark(popularMovieEntity, state)
        }
}