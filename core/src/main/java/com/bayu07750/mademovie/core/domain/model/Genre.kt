package com.bayu07750.mademovie.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
    val posterRaw: String,
): Parcelable {
    val poster get() = "https://image.tmdb.org/t/p/w300$posterRaw"
}