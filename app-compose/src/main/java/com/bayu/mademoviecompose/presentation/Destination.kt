package com.bayu.mademoviecompose.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

const val PARAM_MOVIE = "movieId"

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
    route = "home",
    selectedIcon = com.bayu07750.mademovie.core.R.drawable.home_select,
    unselectedIcon = com.bayu07750.mademovie.core.R.drawable.home_unselect,
    label = com.bayu07750.mademovie.core.R.string.home
)

object CategoryDestination : BottomNavDestination(
    route = "genre",
    selectedIcon = com.bayu07750.mademovie.core.R.drawable.category_select,
    unselectedIcon = com.bayu07750.mademovie.core.R.drawable.category_unselect,
    label = com.bayu07750.mademovie.core.R.string.category
)

object BookmarkDestination : BottomNavDestination(
    route = "bookmark",
    selectedIcon = com.bayu07750.mademovie.core.R.drawable.bookmark_select,
    unselectedIcon = com.bayu07750.mademovie.core.R.drawable.bookmark_unselect,
    label = com.bayu07750.mademovie.core.R.string.bookmark
)
object DetailDestination : Destination(route = "detail/{$PARAM_MOVIE}") {
    fun createRoute(movieId: Int) = "detail/$movieId"
}

object SearchDestination : Destination(route = "search")

object DiscoveryDestination : Destination(route = "disocvery")

