package com.bayu.mademoviecompose.presentation.navigation

import androidx.navigation.NavHostController
import com.bayu.mademoviecompose.presentation.DetailDestination
import com.bayu.mademoviecompose.presentation.SearchDestination

fun NavHostController.navigateToDetail(movieId: Int) {
    navigate(route = DetailDestination.createRoute(movieId))
}

fun NavHostController.navigateToSearch() {
    navigate(route = SearchDestination.route)
}