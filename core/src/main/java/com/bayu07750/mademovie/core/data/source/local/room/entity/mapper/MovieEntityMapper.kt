package com.bayu07750.mademovie.core.data.source.local.room.entity.mapper

import com.bayu07750.mademovie.core.data.local.room.entity.MovieEntity
import com.bayu07750.mademovie.core.data.network.model.MovieResponse
import com.bayu07750.mademovie.core.domain.model.Movie
import kotlin.random.Random

object MovieEntityMapper : EntityMapper<MovieEntity, Movie, MovieResponse> {

    override fun asDomain(entity: MovieEntity): Movie {
        return Movie(id = entity.id, posterRaw = entity.posterRaw)
    }

    override fun asEntityFromDomain(domain: Movie): MovieEntity {
        return MovieEntity(id = domain.id, posterRaw = domain.poster)
    }

    override fun asEntityFromResponse(response: MovieResponse): MovieEntity {
        return MovieEntity(id = response.id ?: Random.nextInt(), posterRaw = response.posterPath.orEmpty())
    }
}