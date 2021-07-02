package com.example.movieku.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.utils.DummyData
import com.example.movieku.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel
    private val dummyUpMovie = DummyData.generateUpMovies()[0]
    private val dummyPopMovie = DummyData.generatePopMovies()[0]
    private val upId = dummyUpMovie.id
    private val popId = dummyPopMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviekuRepository: MoviekuRepository

    @Mock
    private lateinit var upMovieObserver: Observer<Resource<UpcomingMovieEntity>>

    @Mock
    private lateinit var popMovieObserver: Observer<Resource<PopularMovieEntity>>

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(moviekuRepository)
    }

    @Test
    fun getGetUpMovies() {
        detailViewModel.setSelectedItem(upId)
        val dummyMovieWithModule =
            Resource.success(DummyData.generateDummyUpMovieWithModules(dummyUpMovie, true))
        val movie = MutableLiveData<Resource<UpcomingMovieEntity>>()
        movie.value = dummyMovieWithModule

        Mockito.`when`(moviekuRepository.getUpDetailMovie(upId)).thenReturn(movie)
        detailViewModel.getUpMovies.observeForever(upMovieObserver)
        Mockito.verify(upMovieObserver).onChanged(dummyMovieWithModule)

        val expectedValue = movie.value
        val actualValue = detailViewModel.getUpMovies.value

        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data?.id, actualValue?.data?.id)
        assertEquals(expectedValue?.data?.overview, actualValue?.data?.overview)
        assertEquals(expectedValue?.data?.originalLanguage, actualValue?.data?.originalLanguage)
        assertEquals(expectedValue?.data?.originalTitle, actualValue?.data?.originalTitle)
        assertEquals(expectedValue?.data?.video, actualValue?.data?.video)
        assertEquals(expectedValue?.data?.title, actualValue?.data?.title)
        assertEquals(expectedValue?.data?.posterPath, actualValue?.data?.posterPath)
        assertEquals(expectedValue?.data?.popularity, actualValue?.data?.popularity)
        assertEquals(expectedValue?.data?.voteAverage, actualValue?.data?.voteAverage)
        assertEquals(expectedValue?.data?.adult, actualValue?.data?.adult)
        assertEquals(expectedValue?.data?.voteCount, actualValue?.data?.voteCount)
        assertEquals(expectedValue?.data?.bookmarked, actualValue?.data?.bookmarked)
    }


    @Test
    fun getGetPopMovies() {
        detailViewModel.setSelectedItem(popId)
        val dummyMovieWithModule =
            Resource.success(DummyData.generateDummyPopMovieWithModules(dummyPopMovie, true))
        val movie = MutableLiveData<Resource<PopularMovieEntity>>()
        movie.value = dummyMovieWithModule

        Mockito.`when`(moviekuRepository.getPopDetailMovie(popId)).thenReturn(movie)
        detailViewModel.getPopMovies.observeForever(popMovieObserver)
        Mockito.verify(popMovieObserver).onChanged(dummyMovieWithModule)

        val expectedValue = movie.value
        val actualValue = detailViewModel.getPopMovies.value

        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data?.id, actualValue?.data?.id)
        assertEquals(expectedValue?.data?.overview, actualValue?.data?.overview)
        assertEquals(expectedValue?.data?.originalLanguage, actualValue?.data?.originalLanguage)
        assertEquals(expectedValue?.data?.originalTitle, actualValue?.data?.originalTitle)
        assertEquals(expectedValue?.data?.video, actualValue?.data?.video)
        assertEquals(expectedValue?.data?.title, actualValue?.data?.title)
        assertEquals(expectedValue?.data?.posterPath, actualValue?.data?.posterPath)
        assertEquals(expectedValue?.data?.popularity, actualValue?.data?.popularity)
        assertEquals(expectedValue?.data?.voteAverage, actualValue?.data?.voteAverage)
        assertEquals(expectedValue?.data?.adult, actualValue?.data?.adult)
        assertEquals(expectedValue?.data?.voteCount, actualValue?.data?.voteCount)
        assertEquals(expectedValue?.data?.bookmarked, actualValue?.data?.bookmarked)
    }
}