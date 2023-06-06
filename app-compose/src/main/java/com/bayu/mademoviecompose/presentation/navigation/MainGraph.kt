package com.bayu.mademoviecompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.bayu.mademoviecompose.presentation.BookmarkDestination
import com.bayu.mademoviecompose.presentation.CategoryDestination
import com.bayu.mademoviecompose.presentation.DetailDestination
import com.bayu.mademoviecompose.presentation.DiscoveryDestination
import com.bayu.mademoviecompose.presentation.HomeDestination
import com.bayu.mademoviecompose.presentation.PARAM_MOVIE
import com.bayu.mademoviecompose.presentation.SearchDestination
import com.bayu.mademoviecompose.presentation.bookmark.BookmarkScreen
import com.bayu.mademoviecompose.presentation.bookmark.BookmarkViewModel
import com.bayu.mademoviecompose.presentation.category.CategoryScreen
import com.bayu.mademoviecompose.presentation.category.CategoryViewModel
import com.bayu.mademoviecompose.presentation.detail.DetailScreen
import com.bayu.mademoviecompose.presentation.detail.DetailViewModel
import com.bayu.mademoviecompose.presentation.discovery.DiscoveryScreen
import com.bayu.mademoviecompose.presentation.home.HomeScreen
import com.bayu.mademoviecompose.presentation.home.HomeViewModel
import com.bayu.mademoviecompose.presentation.search.SearchScreen
import com.bayu.mademoviecompose.presentation.search.SearchViewModel
import org.koin.androidx.compose.koinViewModel

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
                val viewModel: HomeViewModel = koinViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                HomeScreen(
                    uiState = uiState,
                    onRetry = viewModel::getData,
                    onClickedMovie = { movie -> navController.navigateToDetail(movie.id) },
                    onClickedButtonSeeMore = {},
                    onSearch = { navController.navigateToSearch() },
                    onChangeLanguage = viewModel::setLanguage,
                    onChangeTrendingTimeWindow = viewModel::setTrendingTimeWindow,
                )
            }

            composable(
                route = CategoryDestination.route
            ) {
                val viewModel = koinViewModel<CategoryViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                CategoryScreen(
                    uiState = uiState,
                    onClickedItemGenre = { genre ->
                        // TODO:
                    }
                )
            }

            composable(
                route = BookmarkDestination.route
            ) {
                val viewModel = koinViewModel<BookmarkViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                BookmarkScreen(
                    uiState = uiState,
                    onMovieClicked = { movie ->
                        navController.navigateToDetail(movie.id)
                    }
                )
            }

            composable(
                route = DetailDestination.route,
                arguments = listOf(
                    navArgument(name = PARAM_MOVIE) {
                        type = NavType.IntType
                        nullable = false
                        defaultValue = -1
                    }
                ),
            ) {
                val viewModel: DetailViewModel = koinViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                DetailScreen(
                    uiState = uiState,
                    onBack = {
                        navController.navigateUp()
                    },
                    onBookmarked = viewModel::bookmarkMovie,
                    onGenreClicked = { /*TODO*/ },
                )
            }

            composable(
                route = DiscoveryDestination.route
            ) {
                DiscoveryScreen()
            }

            composable(
                route = SearchDestination.route
            ) {
                val viewModel: SearchViewModel = koinViewModel()
                val movies = viewModel.movies.collectAsLazyPagingItems()
                SearchScreen(
                    movies = movies,
                    onSearch = viewModel::setQuery,
                    onMovieClicked = { movie -> navController.navigateToDetail(movie.id) },
                    onBack = { navController.navigateUp() },
                )
            }
        }
    )
}