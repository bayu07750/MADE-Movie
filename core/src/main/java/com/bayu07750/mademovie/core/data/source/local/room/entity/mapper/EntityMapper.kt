package com.bayu07750.mademovie.core.data.source.local.room.entity.mapper

interface EntityMapper<Entity, Domain, Response> {
    fun asDomain(entity: Entity): Domain
    fun asEntityFromDomain(domain: Domain): Entity
    fun asEntityFromResponse(response: Response): Entity
}