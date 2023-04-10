package com.bayu07750.mademovie.core.data.network.model


import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val castResponse: List<CastResponse>?,
)