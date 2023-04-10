package com.bayu07750.mademovie.core.domain.model

data class MovieDetail(
    val id: Int,
    val name: String,
    val genres: List<Genre>,
    val casts: List<Cast>,
    val posterRaw: String,
    val synopsis: String,
    val bookmarked: Boolean = false,
) {
    val poster get() = "https://image.tmdb.org/t/p/w300$posterRaw"

    fun asMovie() = Movie(id, posterRaw, bookmarked)
}