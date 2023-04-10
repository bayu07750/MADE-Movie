package com.bayu07750.mademovie.core.data.local.room.entity.mapper

interface EntityMapper<Entity, Response> {
    fun asEntity(response: Response): Entity
}