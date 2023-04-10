package com.bayu07750.mademovie.core.data.source.local.room.entity.mapper

import com.bayu07750.mademovie.core.data.local.room.entity.GenreEntity
import com.bayu07750.mademovie.core.data.model.response.GenreResponse
import com.bayu07750.mademovie.core.domain.model.Genre
import kotlin.random.Random

object GenreEntityMapper : EntityMapper<GenreEntity, Genre, GenreResponse> {

    override fun asDomain(entity: GenreEntity): Genre {
        return Genre(id = entity.id, name = entity.name, posterRaw = entity.posterRaw)
    }

    override fun asEntityFromDomain(domain: Genre): GenreEntity {
        return GenreEntity(id = domain.id, name = domain.name, posterRaw = domain.posterRaw)
    }

    override fun asEntityFromResponse(response: GenreResponse): GenreEntity {
        return GenreEntity(
            id = response.id ?: Random.nextInt(),
            name = response.name.orEmpty(),
            posterRaw = response.posterRaw.orEmpty()
        )
    }
}