package com.example.movieku.ui.detail

import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity

interface DetailActivityCallback {

    fun onUpMovieShareClick(upcomingMovieEntity: UpcomingMovieEntity)

    fun onPopMovieShareClick(popularMovieEntity: PopularMovieEntity)

}