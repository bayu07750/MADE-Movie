package com.bayu07750.mademovie.core.data.source.network.model.mapper

import com.bayu07750.mademovie.core.data.network.model.MovieResponse
import com.bayu07750.mademovie.core.domain.model.Movie

object MovieResponseMapper : ResponseMapper<MovieResponse, Movie> {

    override fun invoke(response: MovieResponse): Movie = Movie(
        id = response.id ?: 0,
        posterRaw = response.posterPath.orEmpty()
    )
}