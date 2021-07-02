package com.example.movieku.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieku.data.source.remote.response.PopularMovieResponse
import com.example.movieku.data.source.remote.response.ResultsItem
import com.example.movieku.data.source.remote.response.UpcomingMovieResponse
import com.example.movieku.data.source.remote.service.ApiClient
import com.example.movieku.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    private val listUpcomingMovie = MutableLiveData<ApiResponse<List<ResultsItem>>>()
    private val listPopularMovie = MutableLiveData<ApiResponse<List<ResultsItem>>>()

    fun getAllUpcomingMovies(): LiveData<ApiResponse<List<ResultsItem>>> {
        EspressoIdlingResource.increment()
        val client = ApiClient.getApiService().getUpcomingMovies()
        client.enqueue(object : Callback<UpcomingMovieResponse?> {
            override fun onResponse(
                call: Call<UpcomingMovieResponse?>,
                response: Response<UpcomingMovieResponse?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.results as List<ResultsItem>
                    listUpcomingMovie.postValue(ApiResponse.success(responseBody))
                    if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow)
                        EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<UpcomingMovieResponse?>, t: Throwable) {
                listUpcomingMovie.value = null
                Log.d("upcomingMovies Failed", t.stackTraceToString())
            }
        })
        return listUpcomingMovie
    }

    fun getAllPopularMovies(): LiveData<ApiResponse<List<ResultsItem>>> {
        EspressoIdlingResource.increment()
        val client = ApiClient.getApiService().getPopularMovies()
        client.enqueue(object : Callback<PopularMovieResponse?> {
            override fun onResponse(
                call: Call<PopularMovieResponse?>,
                response: Response<PopularMovieResponse?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.results as List<ResultsItem>
                    listPopularMovie.postValue(ApiResponse.success(responseBody))
                    if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow)
                        EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<PopularMovieResponse?>, t: Throwable) {
                listPopularMovie.value = null
                Log.d("popularMovies Failed", t.stackTraceToString())
            }
        })
        return listPopularMovie
    }
}