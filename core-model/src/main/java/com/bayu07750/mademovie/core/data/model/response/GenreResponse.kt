package com.bayu07750.mademovie.core.data.model.response


import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("posterRaw")
    val posterRaw: String?,
)