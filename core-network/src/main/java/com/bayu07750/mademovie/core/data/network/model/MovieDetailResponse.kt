package com.bayu07750.mademovie.core.data.network.model


import com.bayu07750.mademovie.core.data.model.response.GenreResponse
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<GenreResponse>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyResponse?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryResponse?>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageResposne?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {

    data class SpokenLanguageResposne(
        @SerializedName("english_name")
        val englishName: String?,
        @SerializedName("iso_639_1")
        val iso6391: String?,
        @SerializedName("name")
        val name: String?
    )

    data class ProductionCountryResponse(
        @SerializedName("iso_3166_1")
        val iso31661: String?,
        @SerializedName("name")
        val name: String?
    )

    data class ProductionCompanyResponse(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("logo_path")
        val logoPath: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("origin_country")
        val originCountry: String?
    )
}