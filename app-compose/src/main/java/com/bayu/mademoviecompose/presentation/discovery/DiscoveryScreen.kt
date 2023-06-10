package com.bayu.mademoviecompose.presentation.discovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.bayu07750.mademovie.core.R
import com.bayu.mademoviecompose.presentation.component.AppTopBar
import com.bayu.mademoviecompose.presentation.component.CenterBox
import com.bayu.mademoviecompose.presentation.home.MovieItem
import com.bayu.mademoviecompose.presentation.search.Error
import com.bayu.mademoviecompose.presentation.util.JCallback
import com.bayu.mademoviecompose.presentation.util.JCallbackType
import com.bayu07750.mademovie.core.domain.model.Movie

@Composable
fun DiscoveryScreen(
    title: String,
    movies: LazyPagingItems<Movie>,
    onBack: JCallback,
    onMovieClicked: JCallbackType<Movie>,
    modifier: Modifier = Modifier,
) {
    val isLoading = movies.loadState.refresh is LoadState.Loading
    val isError = movies.loadState.refresh is LoadState.Error
    val message = (movies.loadState.refresh as? LoadState.Error)?.error?.message
    val isEmpty = !isLoading && !isError && movies.itemCount == 0

    Scaffold(
        modifier = modifier
            .statusBarsPadding(),
        topBar = {
            AppTopBar(
                title = title,
                iconBack = true,
                onClick = onBack
            )
        },
    ) { innerPadding ->
        val innerModifier = Modifier.padding(innerPadding)

        if (isLoading) {
            CenterBox(modifier = innerModifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else if (isError) {
            Error(
                message = message.orEmpty().ifEmpty { stringResource(id = R.string.message_error) },
                onRetry = movies::retry,
                modifier = innerModifier.fillMaxSize().padding(horizontal = 16.dp)
            )
        } else if (isEmpty) {
            CenterBox(modifier = innerModifier.fillMaxSize()) {
                Text(text = stringResource(id = R.string.no_data))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = innerModifier,
            ) {
                items(
                    count = movies.itemCount,
                    key = movies.itemKey { it.id },
                ) { index ->
                    movies[index]?.let { movie ->
                        MovieItem(
                            image = movie.poster,
                            onClick = { onMovieClicked.invoke(movie) }
                        )
                    }
                }

                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    if (movies.loadState.append is LoadState.Loading) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else if (movies.loadState.append is LoadState.Error) {
                        Error(
                            message = (movies.loadState.append as LoadState.Error).error.message.orEmpty().ifEmpty {
                                stringResource(id = R.string.message_error)
                            },
                            onRetry = movies::retry,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}