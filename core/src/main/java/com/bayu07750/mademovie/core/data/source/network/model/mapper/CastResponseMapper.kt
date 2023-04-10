package com.bayu07750.mademovie.core.data.source.network.model.mapper

import com.bayu07750.mademovie.core.data.network.model.CastResponse
import com.bayu07750.mademovie.core.domain.model.Cast

object CastResponseMapper : ResponseMapper<CastResponse, Cast> {

    override fun invoke(response: CastResponse): Cast {
        return Cast(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            profileRaw = response.profilePath.orEmpty(),
            character = response.character.orEmpty()
        )
    }
}