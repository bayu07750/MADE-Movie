package com.bayu07750.mademovie.core.domain.model

data class Cast(
    val id: Int,
    val name: String,
    val profileRaw: String,
    val character: String,
) {
    val poster get() = "https://image.tmdb.org/t/p/w300$profileRaw"
}