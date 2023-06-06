package com.bayu.mademoviecompose.presentation.search

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.bayu.mademoviecompose.presentation.component.AppSearchTopBar
import com.bayu.mademoviecompose.presentation.component.CenterBox
import com.bayu.mademoviecompose.presentation.home.MovieItem
import com.bayu.mademoviecompose.presentation.util.JCallback
import com.bayu.mademoviecompose.presentation.util.JCallbackString
import com.bayu.mademoviecompose.presentation.util.JCallbackType
import com.bayu07750.mademovie.core.R
import com.bayu07750.mademovie.core.domain.model.Movie
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    movies: LazyPagingItems<Movie>,
    onBack: JCallback,
    onSearch: JCallbackString,
    onMovieClicked: JCallbackType<Movie>,
    modifier: Modifier = Modifier,
) {
    val isLoading = movies.loadState.refresh is LoadState.Loading
    val isError = movies.loadState.refresh is LoadState.Error
    val message = (movies.loadState.refresh as? LoadState.Error)?.error?.message
    val isEmpty = !isLoading && !isError && movies.itemCount == 0

    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        snapshotFlow { query }
            .distinctUntilChanged()
            .debounce(800)
            .collect { query ->
                onSearch.invoke(query)
            }
    }

    Scaffold(
        modifier = modifier
            .statusBarsPadding(),
        topBar = {
            AppSearchTopBar(
                query = query,
                onQueryChange = { query = it },
                onBack = onBack,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
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

@Composable
fun Error(
    message: String,
    onRetry: JCallback,
    modifier: Modifier = Modifier,
) {
    CenterBox(
        modifier = modifier
    ) {
        Text(text = message.ifEmpty { stringResource(id = R.string.message_error) })
        Button(onClick = onRetry, shape = CircleShape) {
            Text(text = stringResource(id = R.string.button_retry))
        }
    }
}