package com.example.movieku.ui.home.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.vo.Resource

class PopMovieViewModel(private val moviekuRepository: MoviekuRepository) : ViewModel() {
    fun getPopcomingMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>> =
        moviekuRepository.getPopularMovies()
}