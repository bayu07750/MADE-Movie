package com.bayu07750.mademovie.core.data.model.response

import com.google.gson.annotations.SerializedName

data class ListGenreResponse(
    @SerializedName("genres")
    val genres: List<GenreResponse>,
)