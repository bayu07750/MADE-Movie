package com.bayu.mademoviecompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bayu.mademoviecompose.presentation.CategoryDestination
import com.bayu.mademoviecompose.presentation.BookmarkDestination
import com.bayu.mademoviecompose.presentation.DetailDestination
import com.bayu.mademoviecompose.presentation.DiscoveryDestination
import com.bayu.mademoviecompose.presentation.HomeDestination
import com.bayu.mademoviecompose.presentation.SearchDestination
import com.bayu.mademoviecompose.presentation.bookmark.BookmarkScreen
import com.bayu.mademoviecompose.presentation.category.CategoryScreen
import com.bayu.mademoviecompose.presentation.detail.DetailScreen
import com.bayu.mademoviecompose.presentation.discovery.DiscoveryScreen
import com.bayu.mademoviecompose.presentation.home.HomeScreen
import com.bayu.mademoviecompose.presentation.search.SearchScreen

@Composable
fun MainGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        builder = {
            composable(
                route = HomeDestination.route,
            ) {
                HomeScreen()
            }

            composable(
                route = CategoryDestination.route
            ) {
                CategoryScreen()
            }

            composable(
                route = BookmarkDestination.route
            ) {
                BookmarkScreen()
            }

            composable(
                route = DetailDestination.route
            ) {
                DetailScreen()
            }

            composable(
                route = DiscoveryDestination.route
            ) {
                DiscoveryScreen()
            }

            composable(
                route = SearchDestination.route
            ) {
                SearchScreen()
            }
        }
    )
}