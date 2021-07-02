package com.example.movieku.ui.home.upcoming

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpMovieViewModelTest {

    private lateinit var upMovieViewModel: UpMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviekuRepository: MoviekuRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<UpcomingMovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<UpcomingMovieEntity>

    @Before
    fun setup() {
        upMovieViewModel = UpMovieViewModel(moviekuRepository)
    }

    @Test
    fun getUpcomingMovies() {
        val dummyMovies = Resource.success(pagedList)
        Mockito.`when`(dummyMovies.data?.size).thenReturn(1)
        val movies = MutableLiveData<Resource<PagedList<UpcomingMovieEntity>>>()
        movies.value = dummyMovies

        Mockito.`when`(moviekuRepository.getUpcomingMovies()).thenReturn(movies)
        val movieEntities = upMovieViewModel.getUpcomingMovies().value?.data
        Mockito.verify(moviekuRepository).getUpcomingMovies()
        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        upMovieViewModel.getUpcomingMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }
}