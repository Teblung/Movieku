package com.example.movieku.ui.favorite.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.PopularMovieEntity

class FavPopMovieViewModel(private val moviekuRepository: MoviekuRepository) : ViewModel() {
    fun getBookmarks(): LiveData<PagedList<PopularMovieEntity>> {
        return moviekuRepository.getPopBookmarkedMovies()
    }

    fun setBookmark(popularMovieEntity: PopularMovieEntity) {
        val newState = !popularMovieEntity.bookmarked
        moviekuRepository.setPopBookmarkedMovie(popularMovieEntity, newState)
    }
}