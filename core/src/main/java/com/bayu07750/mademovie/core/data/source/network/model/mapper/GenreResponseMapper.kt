package com.bayu07750.mademovie.core.data.source.network.model.mapper

import com.bayu07750.mademovie.core.data.model.response.GenreResponse
import com.bayu07750.mademovie.core.domain.model.Genre

object GenreResponseMapper : ResponseMapper<GenreResponse, Genre> {

    override fun invoke(response: GenreResponse): Genre {
        return Genre(id = response.id ?: 0, name = response.name.orEmpty(), posterRaw = response.posterRaw.orEmpty())
    }
}