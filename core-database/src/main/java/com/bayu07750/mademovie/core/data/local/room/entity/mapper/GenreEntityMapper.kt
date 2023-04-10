package com.bayu07750.mademovie.core.data.local.room.entity.mapper

import com.bayu07750.mademovie.core.data.model.response.GenreResponse
import com.bayu07750.mademovie.core.data.local.room.entity.GenreEntity

object GenreEntityMapper : EntityMapper<GenreEntity, GenreResponse> {

    override fun asEntity(response: GenreResponse): GenreEntity {
        return GenreEntity(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            posterRaw = response.posterRaw.orEmpty()
        )
    }
}