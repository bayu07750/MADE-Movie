package com.bayu07750.mademovie.utils

import com.bayu07750.mademovie.core.domain.model.Cast
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.model.MovieDetail

object DataDummy {

    val movie = Movie(100, "testing raw", true)
    val movie2 = Movie(1001, "testing raw", true)
    val movies = listOf(movie, movie2)
    val emptyMovies: List<Movie> = emptyList()

    val genre = Genre(id = 1, "Action", "testing 1")
    val genre2 = Genre(id = 2, "Advanture", "testing 2")
    val genres = listOf(genre, genre2)
    val emptyGenres: List<Genre> = emptyList()

    val cast = Cast(1, "Nike", "nike", "Nike")
    val cas2 = Cast(2, "Adidas", "adidas", "Adidas")
    val casts = listOf(cast, cas2)
    val emptyCasts: List<Cast> = emptyList()

    val movieDetail = MovieDetail(
        id = 1,
        name = "Naruto",
        genres = listOf(),
        casts = listOf(),
        posterRaw = "",
        synopsis = "",
        bookmarked = false
    )

    val movieDetailAndCasts = movieDetail.copy(
        casts = casts,
    )

    fun generateDummyMovies() = List(100) {
        Movie(id = it, posterRaw = "$it", bookmarked = false)
    }

    fun generateDummyGenres() = List(200) {
        Genre(id = it, name = "$it", posterRaw = "$it poster")
    }
}