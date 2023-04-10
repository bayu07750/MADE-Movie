package com.bayu07750.mademovie.core.data.source.network.model.mapper

import com.bayu07750.mademovie.core.data.network.model.MovieDetailResponse
import com.bayu07750.mademovie.core.domain.model.MovieDetail

object MovieDetailMapper : ResponseMapper<MovieDetailResponse, MovieDetail> {

    override fun invoke(response: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            id = response.id ?: 0,
            name = response.title.orEmpty(),
            genres = response.genres?.map { GenreResponseMapper(it) } ?: emptyList(),
            casts = emptyList(),
            posterRaw = response.posterPath.orEmpty(),
            synopsis = response.overview.orEmpty()
        )
    }
}