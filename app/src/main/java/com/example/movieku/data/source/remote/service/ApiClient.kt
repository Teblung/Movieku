package com.example.movieku.data.source.remote.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY_VALUE = "ac3d3f09e9a794a2bbe784f494eb7aee"
        private var retrofit: Retrofit? = null

        private fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                val loggingInterceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

                val httpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", API_KEY_VALUE)
                        .build()
                    val requestBuilder = original.newBuilder().url(url)
                    val request = requestBuilder.build()

                    chain.proceed(request)
                }).addInterceptor(loggingInterceptor).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

        fun getApiService(): ApiService =
            getRetrofitInstance()?.create(ApiService::class.java) as ApiService

    }
}