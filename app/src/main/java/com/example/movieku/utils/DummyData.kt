package com.example.movieku.utils

import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.data.source.remote.response.ResultsItem

object DummyData {

    fun generateUpMovies(): List<UpcomingMovieEntity> {
        val moviesList = arrayListOf<UpcomingMovieEntity>()
        upMoviesArrayList.forEach {
            moviesList.add(
                UpcomingMovieEntity(
                    it.id,
                    it.overview,
                    it.originalLanguage,
                    it.originalTitle,
                    it.video,
                    it.title,
                    it.posterPath,
                    it.popularity,
                    it.voteAverage,
                    it.adult,
                    it.voteCount,
                    it.bookmarked
                )
            )
        }
        return moviesList
    }

    fun generatePopMovies(): List<PopularMovieEntity> {
        val moviesList = arrayListOf<PopularMovieEntity>()
        popMoviesArrayList.forEach {
            moviesList.add(
                PopularMovieEntity(
                    it.id,
                    it.overview,
                    it.originalLanguage,
                    it.originalTitle,
                    it.video,
                    it.title,
                    it.posterPath,
                    it.popularity,
                    it.voteAverage,
                    it.adult,
                    it.voteCount,
                    it.bookmarked
                )
            )
        }
        return moviesList
    }

    fun generateDummyUpMovieWithModules(
        upcomingMovieEntity: UpcomingMovieEntity,
        bookmarked: Boolean
    ): UpcomingMovieEntity {
        upcomingMovieEntity.bookmarked = bookmarked
        return upcomingMovieEntity
    }

    fun generateDummyPopMovieWithModules(
        popularMovieEntity: PopularMovieEntity,
        bookmarked: Boolean
    ): PopularMovieEntity {
        popularMovieEntity.bookmarked = bookmarked
        return popularMovieEntity
    }

    private val upMoviesArrayList = arrayListOf(
        ResultsItem(
            379686,
            "When LeBron and his young son Dom are trapped in a digital space by a rogue A.I., LeBron must get them home safe by leading Bugs, Lola Bunny and the whole gang of notoriously undisciplined Looney Tunes to victory over the A.I.'s digitized champions on the court. It's Tunes versus Goons in the highest-stakes challenge of his life.",
            "en",
            "Space Jam: A New Legacy",
            false,
            "Space Jam: A New Legacy",
            "/boATtcBEjJF7z3HEyWCCpBqRDs4.jpg",
            214.328,
            0.toDouble(),
            false,
            0,
            false
        )
    )

    private val popMoviesArrayList = arrayListOf(
        ResultsItem(
            159211,
            "After a divorce, Sophia moves to the south of Italy with her daughter, Helena. Their new home, an apartment within an austere building of the fascist age, is a chance for them to start a new life. But inside an old storage room hides a mysterious closet and a buried secret. After the loss of Helena’s first baby tooth, a chilling obsession begins and an apparition haunts her sleep...",
            "en",
            "The Haunting of Helena",
            false,
            "The Haunting of Helena",
            "/iB8rf8FYHGlrbmLybCo6Mpg8hNt.jpg",
            1113.596,
            5.2,
            false,
            99,
            false
        )
    )

}