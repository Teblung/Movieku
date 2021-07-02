package com.example.movieku.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UpcomingMovieResponse(

    @field:SerializedName("dates")
    val dates: Dates,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<ResultsItem>,

    @field:SerializedName("total_results")
    val totalResults: Int
)

data class Dates(

    @field:SerializedName("maximum")
    val maximum: String,

    @field:SerializedName("minimum")
    val minimum: String
)
