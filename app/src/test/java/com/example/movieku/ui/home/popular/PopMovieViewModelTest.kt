package com.example.movieku.ui.home.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.PopularMovieEntity
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
class PopMovieViewModelTest {

    private lateinit var popMovieViewModel: PopMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviekuRepository: MoviekuRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<PopularMovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<PopularMovieEntity>

    @Before
    fun setup() {
        popMovieViewModel = PopMovieViewModel(moviekuRepository)
    }

    @Test
    fun getPopcomingMovies() {
        val dummyMovies = Resource.success(pagedList)
        Mockito.`when`(dummyMovies.data?.size).thenReturn(1)
        val movies = MutableLiveData<Resource<PagedList<PopularMovieEntity>>>()
        movies.value = dummyMovies

        Mockito.`when`(moviekuRepository.getPopularMovies()).thenReturn(movies)
        val movieEntities = popMovieViewModel.getPopcomingMovies().value?.data
        Mockito.verify(moviekuRepository).getPopularMovies()
        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        popMovieViewModel.getPopcomingMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }
}