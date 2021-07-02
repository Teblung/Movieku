package com.example.movieku.ui.favorite.upcoming

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.nhaarman.mockitokotlin2.verify
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
class FavUpMovieViewModelTest {

    private lateinit var favUpMovieViewModel: FavUpMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviekuRepository: MoviekuRepository

    @Mock
    private lateinit var observer: Observer<PagedList<UpcomingMovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<UpcomingMovieEntity>

    @Before
    fun setup() {
        favUpMovieViewModel = FavUpMovieViewModel(moviekuRepository)
    }

    @Test
    fun getBookmarks() {
        val dummyMovies = pagedList
        Mockito.`when`(dummyMovies.size).thenReturn(1)
        val movies = MutableLiveData<PagedList<UpcomingMovieEntity>>()
        movies.value = dummyMovies

        Mockito.`when`(moviekuRepository.getUpBookmarkedMovies()).thenReturn(movies)
        val movieEntities = favUpMovieViewModel.getBookmarks().value
        verify(moviekuRepository).getUpBookmarkedMovies()
        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        favUpMovieViewModel.getBookmarks().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}