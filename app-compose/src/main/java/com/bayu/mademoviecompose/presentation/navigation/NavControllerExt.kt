package com.bayu.mademoviecompose.presentation.navigation

import androidx.navigation.NavHostController
import com.bayu.mademoviecompose.presentation.DetailDestination
import com.bayu.mademoviecompose.presentation.DiscoveryDestination
import com.bayu.mademoviecompose.presentation.SearchDestination

fun NavHostController.navigateToDetail(movieId: Int) {
    navigate(route = DetailDestination.createRoute(movieId))
}

fun NavHostController.navigateToSearch() {
    navigate(route = SearchDestination.route)
}

fun NavHostController.navigateDiscoveryGenre(genreId: Int) =
    navigate(DiscoveryDestination.createRouteTypeGenre(genreId) )

fun NavHostController.navigateDiscoveryTrending(timeWindow: String, title: String) =
    navigate(DiscoveryDestination.createRouteTypeTrending(timeWindow))

fun NavHostController.navigateDiscoveryPopular() =
    navigate(DiscoveryDestination.createRouteTypePopular())

fun NavHostController.navigateDiscoveryNowPlaying() = navigate(DiscoveryDestination.createRouteTypeNowPlaying())