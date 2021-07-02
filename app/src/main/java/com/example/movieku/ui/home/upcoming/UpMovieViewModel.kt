package com.example.movieku.ui.home.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.vo.Resource

class UpMovieViewModel(private val moviekuRepository: MoviekuRepository) : ViewModel() {
    fun getUpcomingMovies(): LiveData<Resource<PagedList<UpcomingMovieEntity>>> =
        moviekuRepository.getUpcomingMovies()
}