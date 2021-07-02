package com.example.movieku.ui.favorite.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity

class FavUpMovieViewModel(private val moviekuRepository: MoviekuRepository) : ViewModel() {
    fun getBookmarks(): LiveData<PagedList<UpcomingMovieEntity>> {
        return moviekuRepository.getUpBookmarkedMovies()
    }

    fun setBookmark(upcomingMovieEntity: UpcomingMovieEntity) {
        val newState = !upcomingMovieEntity.bookmarked
        moviekuRepository.setUpBookmarkedMovie(upcomingMovieEntity, newState)
    }
}