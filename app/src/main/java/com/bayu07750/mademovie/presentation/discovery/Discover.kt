package com.bayu07750.mademovie.presentation.discovery

import androidx.annotation.Keep

@Keep
sealed class Discover {
    class Trending(val timeWindow: String) : Discover()
    class ByGenre(val genreId: Int) : Discover()
    object NowPlaying : Discover()
    object Popular : Discover()
}