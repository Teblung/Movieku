package com.example.movieku.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieku.data.MoviekuRepository
import com.example.movieku.di.Injection
import com.example.movieku.ui.detail.DetailViewModel
import com.example.movieku.ui.favorite.popular.FavPopMovieViewModel
import com.example.movieku.ui.favorite.upcoming.FavUpMovieViewModel
import com.example.movieku.ui.home.popular.PopMovieViewModel
import com.example.movieku.ui.home.upcoming.UpMovieViewModel

class ViewModelFactory private constructor(private val moviekuRepository: MoviekuRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UpMovieViewModel::class.java) -> {
                UpMovieViewModel(moviekuRepository) as T
            }
            modelClass.isAssignableFrom(PopMovieViewModel::class.java) -> {
                PopMovieViewModel(moviekuRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(moviekuRepository) as T
            }
            modelClass.isAssignableFrom(FavUpMovieViewModel::class.java) -> {
                FavUpMovieViewModel(moviekuRepository) as T
            }
            modelClass.isAssignableFrom(FavPopMovieViewModel::class.java) -> {
                FavPopMovieViewModel(moviekuRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}