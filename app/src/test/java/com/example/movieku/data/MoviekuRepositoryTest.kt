package com.example.movieku.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieku.data.source.local.LocalDataSource
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.data.source.remote.RemoteDataSource
import com.example.movieku.utils.AppExecutors
import com.example.movieku.utils.DummyData
import com.example.movieku.utils.LiveDataTestUtil
import com.example.movieku.utils.PagedListUtil
import com.example.movieku.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MoviekuRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val moviekuRepository = FakeMoviekuRepository(remote, local, appExecutors)

    private val upMovieResponse = DummyData.generateUpMovies()
    private val upId = upMovieResponse[0].id

    private val popMovieResponse = DummyData.generatePopMovies()
    private val popId = popMovieResponse[0].id

    @Test
    fun getUpcomingMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, UpcomingMovieEntity>
        Mockito.`when`(local.getUpcomingMovies()).thenReturn(dataSourceFactory)
        moviekuRepository.getUpcomingMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DummyData.generateUpMovies()))
        verify(local).getUpcomingMovies()
        assertNotNull(movieEntities.data)
        assertEquals(upMovieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getPopularMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, PopularMovieEntity>
        Mockito.`when`(local.getPopularMovies()).thenReturn(dataSourceFactory)
        moviekuRepository.getPopularMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DummyData.generatePopMovies()))
        verify(local).getPopularMovies()
        assertNotNull(movieEntities.data)
        assertEquals(popMovieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getUpDetailMovie() {
        val dummyMovie = MutableLiveData<UpcomingMovieEntity>()
        dummyMovie.value = DummyData.generateDummyUpMovieWithModules(
            DummyData.generateUpMovies()[0], false
        )
        Mockito.`when`(local.getUpMovieDetail(upId)).thenReturn(dummyMovie)

        val movieEntities =
            LiveDataTestUtil.getValue(moviekuRepository.getUpDetailMovie(upId))
        verify(local).getUpMovieDetail(upId)
        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.title)
        assertEquals(upMovieResponse[0].title, movieEntities.data?.title)
    }

    @Test
    fun getPopDetailMovie() {
        val dummyMovie = MutableLiveData<PopularMovieEntity>()
        dummyMovie.value = DummyData.generateDummyPopMovieWithModules(
            DummyData.generatePopMovies()[0], false
        )
        Mockito.`when`(local.getPopMovieDetail(popId)).thenReturn(dummyMovie)

        val movieEntities =
            LiveDataTestUtil.getValue(moviekuRepository.getPopDetailMovie(popId))
        verify(local).getPopMovieDetail(popId)
        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.title)
        assertEquals(popMovieResponse[0].title, movieEntities.data?.title)
    }

    @Test
    fun getUpBookmarkedMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, UpcomingMovieEntity>
        Mockito.`when`(local.getUpBookmarkedMovies()).thenReturn(dataSourceFactory)
        moviekuRepository.getUpBookmarkedMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DummyData.generateUpMovies()))
        verify(local).getUpBookmarkedMovies()
        assertNotNull(movieEntities)
        assertEquals(upMovieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getPopBookmarkedMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, PopularMovieEntity>
        Mockito.`when`(local.getPopBookmarkedMovies()).thenReturn(dataSourceFactory)
        moviekuRepository.getPopBookmarkedMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DummyData.generatePopMovies()))
        verify(local).getPopBookmarkedMovies()
        assertNotNull(movieEntities)
        assertEquals(popMovieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }
}