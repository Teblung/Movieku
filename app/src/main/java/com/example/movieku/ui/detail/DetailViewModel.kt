package com.example.movieku.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.vo.Resource

class DetailViewModel(private val moviekuRepository: MoviekuRepository) : ViewModel() {
    val itemId = MutableLiveData<Int>()

    fun setSelectedItem(itemId: Int) {
        this.itemId.value = itemId
    }

    var getUpMovies: LiveData<Resource<UpcomingMovieEntity>> = Transformations.switchMap(itemId) {
        moviekuRepository.getUpDetailMovie(it)
    }

    var getPopMovies: LiveData<Resource<PopularMovieEntity>> = Transformations.switchMap(itemId) {
        moviekuRepository.getPopDetailMovie(it)
    }

    fun setUpBookmarkMovie() {
        val moduleMovieResource = getUpMovies.value
        if (moduleMovieResource != null) {
            val movieWithModule = moduleMovieResource.data
            if (movieWithModule != null) {
                val newState = !movieWithModule.bookmarked
                moviekuRepository.setUpBookmarkedMovie(movieWithModule, newState)
            }
        }
    }

    fun setPopBookmarkMovie() {
        val moduleMovieResource = getPopMovies.value
        if (moduleMovieResource != null) {
            val movieWithModule = moduleMovieResource.data
            if (movieWithModule != null) {
                val newState = !movieWithModule.bookmarked
                moviekuRepository.setPopBookmarkedMovie(movieWithModule, newState)
            }
        }
    }
}