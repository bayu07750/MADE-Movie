package com.bayu.mademoviecompose.presentation.navigation

import androidx.navigation.NavHostController
import com.bayu.mademoviecompose.presentation.DetailDestination

fun NavHostController.navigateToDetail(movieId: Int) {
    navigate(route = DetailDestination.createRoute(movieId))
}