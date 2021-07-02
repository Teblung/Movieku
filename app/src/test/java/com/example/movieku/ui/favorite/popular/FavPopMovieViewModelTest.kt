package com.example.movieku.ui.favorite.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.PopularMovieEntity
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
class FavPopMovieViewModelTest {

    private lateinit var favPopMovieViewModel: FavPopMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviekuRepository: MoviekuRepository

    @Mock
    private lateinit var observer: Observer<PagedList<PopularMovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<PopularMovieEntity>

    @Before
    fun setup() {
        favPopMovieViewModel = FavPopMovieViewModel(moviekuRepository)
    }

    @Test
    fun getBookmarks() {
        val dummyMovies = pagedList
        Mockito.`when`(dummyMovies.size).thenReturn(1)
        val movies = MutableLiveData<PagedList<PopularMovieEntity>>()
        movies.value = dummyMovies

        Mockito.`when`(moviekuRepository.getPopBookmarkedMovies()).thenReturn(movies)
        val movieEntities = favPopMovieViewModel.getBookmarks().value
        verify(moviekuRepository).getPopBookmarkedMovies()
        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        favPopMovieViewModel.getBookmarks().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}