package com.bayu.mademoviecompose.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bayu.mademoviecompose.presentation.discovery.DiscoveryType

const val PARAM_MOVIE = "movieId"
const val PARAM_TYPE = "type"
const val PARAM_GENRE_ID = "genreid"
const val PARAM_TRENDING_TIME_WINDOW = "trendingtimewindow"

private const val ROUTE_HOME = "home"
private const val ROUTE_CATEGORY = "category"
private const val ROUTE_BOOKMARK = "bookmark"
private const val ROUTE_DETAIL = "detail"
private const val ROUTE_SEARCH = "search"
private const val ROUTE_DISCOVERY = "discovery"

sealed class Destination(val route: String) {
    companion object
}
sealed class BottomNavDestination(
    route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val label: Int,
) : Destination(route) {
    companion object
}

fun BottomNavDestination.Companion.getAllBottomNavDestination() = buildList {
    add(HomeDestination)
    add(CategoryDestination)
    add(BookmarkDestination)
}

fun Destination.Companion.getAllDestination() = buildList {
    add(HomeDestination)
    add(CategoryDestination)
    add(BookmarkDestination)
    add(DetailDestination)
    add(SearchDestination)
    add(DiscoveryDestination)
}


object HomeDestination : BottomNavDestination(
    route = ROUTE_HOME,
    selectedIcon = com.bayu07750.mademovie.core.R.drawable.home_select,
    unselectedIcon = com.bayu07750.mademovie.core.R.drawable.home_unselect,
    label = com.bayu07750.mademovie.core.R.string.home
)

object CategoryDestination : BottomNavDestination(
    route = ROUTE_CATEGORY,
    selectedIcon = com.bayu07750.mademovie.core.R.drawable.category_select,
    unselectedIcon = com.bayu07750.mademovie.core.R.drawable.category_unselect,
    label = com.bayu07750.mademovie.core.R.string.category
)

object BookmarkDestination : BottomNavDestination(
    route = ROUTE_BOOKMARK,
    selectedIcon = com.bayu07750.mademovie.core.R.drawable.bookmark_select,
    unselectedIcon = com.bayu07750.mademovie.core.R.drawable.bookmark_unselect,
    label = com.bayu07750.mademovie.core.R.string.bookmark
)
object DetailDestination : Destination(route = "$ROUTE_DETAIL/{$PARAM_MOVIE}") {
    fun createRoute(movieId: Int) = "$ROUTE_DETAIL/$movieId"
}

object SearchDestination : Destination(route = ROUTE_SEARCH)

object DiscoveryDestination : Destination(route = "$ROUTE_DISCOVERY/{$PARAM_TYPE}?$PARAM_GENRE_ID={$PARAM_GENRE_ID}&$PARAM_TRENDING_TIME_WINDOW={$PARAM_TRENDING_TIME_WINDOW}") {

    private const val defaultGenreId = -1
    private const val defaultTrendingWindow = ""

    fun createRouteTypeGenre(genreId: Int): String {
        return "$ROUTE_DISCOVERY/${DiscoveryType.GENRE.name}?$PARAM_GENRE_ID=$genreId"
    }

    fun createRouteTypeTrending(timeWindow: String): String {
        return "$ROUTE_DISCOVERY/${DiscoveryType.TRENDING.name}?$PARAM_GENRE_ID=$defaultGenreId&$PARAM_TRENDING_TIME_WINDOW=$timeWindow"
    }

    fun createRouteTypePopular(): String {
        return "$ROUTE_DISCOVERY/${DiscoveryType.POPULAR.name}?$PARAM_GENRE_ID=$defaultGenreId&$PARAM_TRENDING_TIME_WINDOW=$defaultTrendingWindow"
    }

    fun createRouteTypeNowPlaying(): String {
        return "$ROUTE_DISCOVERY/${DiscoveryType.NOW_PLAYING.name}?$PARAM_GENRE_ID=$defaultGenreId&$PARAM_TRENDING_TIME_WINDOW=$defaultTrendingWindow"
    }
}

