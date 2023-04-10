package com.bayu07750.mademovie.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val posterRaw: String,
    val bookmarked: Boolean = false,
) : Parcelable {
    val poster get() = "https://image.tmdb.org/t/p/w300$posterRaw"
}