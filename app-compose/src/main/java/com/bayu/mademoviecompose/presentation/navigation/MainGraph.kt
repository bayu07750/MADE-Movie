package com.bayu.mademoviecompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.bayu.mademoviecompose.presentation.PARAM_GENRE_ID
import com.bayu.mademoviecompose.presentation.PARAM_MOVIE
import com.bayu.mademoviecompose.presentation.PARAM_TRENDING_TIME_WINDOW
import com.bayu.mademoviecompose.presentation.PARAM_TYPE
import com.bayu.mademoviecompose.presentation.SearchDestination
import com.bayu.mademoviecompose.presentation.bookmark.BookmarkScreen
import com.bayu.mademoviecompose.presentation.bookmark.BookmarkViewModel
import com.bayu.mademoviecompose.presentation.category.CategoryScreen
import com.bayu.mademoviecompose.presentation.category.CategoryViewModel
import com.bayu.mademoviecompose.presentation.detail.DetailScreen
import com.bayu.mademoviecompose.presentation.detail.DetailViewModel
import com.bayu.mademoviecompose.presentation.discovery.DiscoveryScreen
import com.bayu.mademoviecompose.presentation.discovery.DiscoveryType
import com.bayu.mademoviecompose.presentation.discovery.DiscoveryViewModel
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
                val context = LocalContext.current
                HomeScreen(
                    uiState = uiState,
                    onRetry = viewModel::getData,
                    onClickedMovie = { movie -> navController.navigateToDetail(movie.id) },
                    onClickedButtonSeeMore = {
                        val trending = context.getString(com.bayu07750.mademovie.core.R.string.trending)
                        val popular = context.getString(com.bayu07750.mademovie.core.R.string.popular)
                        val nowPlaying = context.getString(com.bayu07750.mademovie.core.R.string.now_playing)
                        when (it) {
                            trending -> {
                                val timeWindow = viewModel.uiState.value.trendingTimeWindow
                                navController.navigateDiscoveryTrending(timeWindow.timeWindow, trending)
                            }
                            popular -> navController.navigateDiscoveryPopular()
                            nowPlaying -> navController.navigateDiscoveryNowPlaying()
                            else -> {}
                        }
                    },
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
                        navController.navigateDiscoveryGenre(genre.id)
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
                    onGenreClicked = { genre ->
                        navController.navigateDiscoveryGenre(genre.id)
                    },
                )
            }

            composable(
                route = DiscoveryDestination.route,
                arguments = listOf(
                    navArgument(name = PARAM_TYPE) {
                        nullable = false
                        type = NavType.StringType
                    },
                    navArgument(name = PARAM_GENRE_ID) {
                        type = NavType.IntType
                    },
                    navArgument(name = PARAM_TRENDING_TIME_WINDOW) {
                        nullable = true
                        type = NavType.StringType
                        defaultValue = ""
                    }
                ),
            ) {
                val viewModel: DiscoveryViewModel = koinViewModel()
                val movies = viewModel.movies.collectAsLazyPagingItems()
                val title =
                    when (DiscoveryType.valueOf(it.arguments?.getString(PARAM_TYPE) ?: DiscoveryType.POPULAR.name)) {
                        DiscoveryType.GENRE -> stringResource(id = com.bayu07750.mademovie.core.R.string.genre)
                        DiscoveryType.TRENDING -> stringResource(id = com.bayu07750.mademovie.core.R.string.trending)
                        DiscoveryType.POPULAR -> stringResource(id = com.bayu07750.mademovie.core.R.string.popular)
                        DiscoveryType.NOW_PLAYING -> stringResource(id = com.bayu07750.mademovie.core.R.string.now_playing)
                    }
                DiscoveryScreen(
                    title = title,
                    movies = movies,
                    onBack = { navController.navigateUp() },
                    onMovieClicked = { movie -> navController.navigateToDetail(movie.id) }
                )
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