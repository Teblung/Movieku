package com.example.movieku.data.source.remote.service

import com.example.movieku.data.source.remote.response.DetailMovieResponse
import com.example.movieku.data.source.remote.response.PopularMovieResponse
import com.example.movieku.data.source.remote.response.UpcomingMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/upcoming")
    fun getUpcomingMovies(): Call<UpcomingMovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(): Call<PopularMovieResponse>

    @GET(" movie/{movie_id}")
    fun getDetailMovies(
        @Path(value = "movie_id") movieId: Int
    ): Call<DetailMovieResponse>
}